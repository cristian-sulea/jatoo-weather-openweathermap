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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import jatoo.weather.JaTooWeatherService;

/**
 * Default {@link JaTooWeatherService} implementation for OpenWeatherMap
 * (https://openweathermap.org).
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 2.0, October 18, 2016
 */
public class JaTooWeatherOpenWeatherMap extends AbstractJaTooWeatherOpenWeatherMap {

  /** The API call URL for current weather. */
  private static final String URL_CURRENT_WEATHER = "http://api.openweathermap.org/data/2.5/weather";

  // /** The API call URL for daily forecast weather. */
  // private static final String PATH_FORECAST = "forecast/daily";

  /** The API key. */
  private final String appid;

  /**
   * The constructor.
   * 
   * @param language
   *          the language for which texts are desired
   * @param appid
   *          the API key
   */
  public JaTooWeatherOpenWeatherMap(final String language, final String appid) {
    super(language);
    this.appid = appid;
  }

  /**
   * The constructor.
   * 
   * @param appid
   *          the API key
   */
  public JaTooWeatherOpenWeatherMap(final String appid) {
    this(null, appid);
  }

  @Override
  protected final String getJSONResponse(final String city) throws IOException {

    CloseableHttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpGet request = new HttpGet(URL_CURRENT_WEATHER + "?appid=" + appid + "&id=" + city + "&units=metric");
    CloseableHttpResponse response = client.execute(request);
    BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());

    return EntityUtils.toString(entity, "UTF-8");
  }

}
