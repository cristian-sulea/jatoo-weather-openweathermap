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

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import jatoo.weather.JaTooWeather;
import jatoo.weather.JaTooWeatherService;

/**
 * {@link JaTooWeatherService} implementation for OpenWeatherMap
 * (https://openweathermap.org).
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.1, October 11, 2016
 */
public class JaTooWeatherOpenWeatherMap extends JaTooWeatherService {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooWeatherOpenWeatherMap.class);

  private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/";
  private static final String PATH_CURRENT = "weather";
  // private static final String PATH_FORECAST = "forecast/daily";

  private final String appid;

  public JaTooWeatherOpenWeatherMap(final String language, final String appid) {
    super(language);
    this.appid = appid;
  }

  public JaTooWeatherOpenWeatherMap(final String appid) {
    this(null, appid);
  }

  @Override
  protected JaTooWeather getWeatherImpl(String city) throws Throwable {
    return parseJSON(getResponse(URL_BASE + PATH_CURRENT + "?appid=" + appid + "&id=" + city + "&units=metric"));
  }

  protected String getResponse(String url) throws IOException {

    CloseableHttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpGet request = new HttpGet(url);
    CloseableHttpResponse response = client.execute(request);
    BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());

    return EntityUtils.toString(entity, "UTF-8");
  }

  protected JaTooWeather parseJSON(final String string) {

    JSONObject json = new JSONObject(string);

    if (logger.isDebugEnabled()) {
      logger.debug(json.toString(2));
    }

    JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
    JSONObject jsonMain = json.getJSONObject("main");
    JSONObject jsonWind = json.getJSONObject("wind");
    JSONObject jsonClouds = json.getJSONObject("clouds");
    JSONObject jsonSys = json.getJSONObject("sys");

    JaTooWeather weather = new JaTooWeather();

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

    weather.sunrise = jsonSys.getLong("sunrise") * 1000L;
    weather.sunset = jsonSys.getLong("sunset") * 1000L;

    return weather;
  }

}
