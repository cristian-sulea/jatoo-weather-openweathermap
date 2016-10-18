/*
 * Copyright (C) Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jatoo.weather.openweathermap;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import jatoo.weather.JaTooWeather;
import jatoo.weather.JaTooWeatherService;

/**
 * Abstract {@link JaTooWeatherService} implementation for
 * <code>OpenWeatherMap</code> (https://openweathermap.org).
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, October 18, 2016
 */
public abstract class AbstractJaTooWeatherOpenWeatherMap extends JaTooWeatherService {

  /** The logger. */
  private static final Log LOGGER = LogFactory.getLog(AbstractJaTooWeatherOpenWeatherMap.class);

  /**
   * The constructor.
   * 
   * @param language
   *          the language for which texts are desired
   */
  public AbstractJaTooWeatherOpenWeatherMap(final String language) {
    super(language);
  }

  /**
   * The constructor.
   */
  public AbstractJaTooWeatherOpenWeatherMap() {
    super();
  }

  @Override
  protected final JaTooWeather getWeatherImpl(final String city) throws Throwable {

    JSONObject json = new JSONObject(getJSONResponse(city));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(json.toString(2));
    }

    JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
    JSONObject jsonMain = json.getJSONObject("main");
    JSONObject jsonWind = json.getJSONObject("wind");
    JSONObject jsonClouds = json.getJSONObject("clouds");
    JSONObject jsonSys = json.getJSONObject("sys");

    JaTooWeather weather = new JaTooWeather();

    weather.city = json.getString("name");

    weather.description = jsonWeather.getString("description");

    weather.temperature = jsonMain.getDouble("temp");
    weather.temperatureUnit = JaTooWeather.TEMPERATURE_UNIT.CELSIUS;

    weather.humidity = jsonMain.getInt("humidity");
    weather.humidityUnit = JaTooWeather.HUMIDITY_UNIT.PERCENT;

    weather.pressure = jsonMain.getDouble("pressure");
    weather.pressureUnit = JaTooWeather.PRESSURE_UNIT.HPA;

    weather.wind = jsonWind.getDouble("speed");
    weather.windUnit = JaTooWeather.WIND_UNIT.METER_PER_SEC;

    weather.windDirection = jsonWind.getDouble("deg");
    weather.windDirectionUnit = JaTooWeather.WIND_DIRECTION_UNIT.DEGREES_METEOROLOGICAL;

    weather.clouds = jsonClouds.getInt("all");
    weather.cloudsUnit = JaTooWeather.CLOUDS_UNIT.PERCENT;

    weather.sunrise = TimeUnit.SECONDS.toMillis(jsonSys.getLong("sunrise"));
    weather.sunset = TimeUnit.SECONDS.toMillis(jsonSys.getLong("sunset"));

    return weather;
  }

  /**
   * Gets the OpenWeatherMap response as a JSON object for the specified city.
   * 
   * @param city
   *          the ID of the city
   * 
   * @return a JSON object
   * 
   * @throws Throwable
   *           if there a problem getting the response
   */
  protected abstract String getJSONResponse(String city) throws Throwable;

}
