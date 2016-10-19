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

    JaTooWeather weather = new JaTooWeather(this);

    weather.setCity(json.getString("name"));

    weather.setDescription(jsonWeather.getString("description"));

    weather.setTemperature(jsonMain.getDouble("temp"));
    weather.setTemperatureUnit(JaTooWeather.TEMPERATURE_UNIT.CELSIUS);

    weather.setHumidity(jsonMain.getInt("humidity"));
    weather.setHumidityUnit(JaTooWeather.HUMIDITY_UNIT.PERCENT);

    weather.setPressure(jsonMain.getDouble("pressure"));
    weather.setPressureUnit(JaTooWeather.PRESSURE_UNIT.HPA);

    weather.setWind(jsonWind.getDouble("speed"));
    weather.setWindUnit(JaTooWeather.WIND_UNIT.METER_PER_SEC);

    weather.setWindDirection(jsonWind.getDouble("deg"));
    weather.setWindDirectionUnit(JaTooWeather.WIND_DIRECTION_UNIT.DEGREES_METEOROLOGICAL);

    weather.setClouds(jsonClouds.getInt("all"));
    weather.setCloudsUnit(JaTooWeather.CLOUDS_UNIT.PERCENT);

    weather.setSunrise(TimeUnit.SECONDS.toMillis(jsonSys.getLong("sunrise")));
    weather.setSunset(TimeUnit.SECONDS.toMillis(jsonSys.getLong("sunset")));

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
