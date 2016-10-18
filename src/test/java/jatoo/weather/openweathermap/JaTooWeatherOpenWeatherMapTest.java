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

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import jatoo.weather.JaTooWeather;
import jatoo.weather.JaTooWeatherService;

public class JaTooWeatherOpenWeatherMapTest {

  private static final String APPID = "0b438dbe7fb5d24ce8ca273834fc988c";
  private static final String CITY = "683506";

  @Test
  public void test1() throws Throwable {

    JaTooWeatherService service = new AbstractJaTooWeatherOpenWeatherMap(APPID) {
      @Override
      protected String getJSONResponse(String url) throws IOException {
        return IOUtils.toString(JaTooWeatherOpenWeatherMapTest.class.getResource("response1.json"));
      }
    };

    JaTooWeather weather = service.getWeather(CITY, false);

    Assert.assertEquals("broken clouds", service.getDescription(weather));
    Assert.assertEquals("Temperature: 14.41 °C", service.getTemperatureWithText(weather));
    Assert.assertEquals("Humidity: 77 %", service.getHumidityWithText(weather));
    Assert.assertEquals("Pressure: 1023.89 hPa", service.getPressureWithText(weather));
    Assert.assertEquals("Wind: 2.42 meters/sec", service.getWindWithText(weather));
    Assert.assertEquals("Wind Direction: 9.50104 degrees (meteorological)", service.getWindDirectionWithText(weather));
    Assert.assertEquals("Clouds: 56 %", service.getCloudsWithText(weather));
    Assert.assertEquals("Rain: -", service.getRainWithText(weather));
    Assert.assertEquals("Snow: -", service.getSnowWithText(weather));
    Assert.assertEquals("Sunrise: 07:21", service.getSunriseWithText(weather));
    Assert.assertEquals("Sunset: 18:43", service.getSunsetWithText(weather));
  }

  @Test
  public void test2() throws Throwable {

    JaTooWeatherService service = new AbstractJaTooWeatherOpenWeatherMap(APPID) {
      @Override
      protected String getJSONResponse(String url) throws IOException {
        return IOUtils.toString(JaTooWeatherOpenWeatherMapTest.class.getResource("response2.json"));
      }
    };

    JaTooWeather weather = service.getWeather(CITY, false);

    Assert.assertEquals("light rain", service.getDescription(weather));
    Assert.assertEquals("Temperature: 11.15 °C", service.getTemperatureWithText(weather));
    Assert.assertEquals("Humidity: 91 %", service.getHumidityWithText(weather));
    Assert.assertEquals("Pressure: 1026.84 hPa", service.getPressureWithText(weather));
    Assert.assertEquals("Wind: 3.62 meters/sec", service.getWindWithText(weather));
    Assert.assertEquals("Wind Direction: 78.5013 degrees (meteorological)", service.getWindDirectionWithText(weather));
    Assert.assertEquals("Clouds: 92 %", service.getCloudsWithText(weather));
    Assert.assertEquals("Rain: -", service.getRainWithText(weather));
    Assert.assertEquals("Snow: -", service.getSnowWithText(weather));
    Assert.assertEquals("Sunrise: 07:25", service.getSunriseWithText(weather));
    Assert.assertEquals("Sunset: 18:38", service.getSunsetWithText(weather));
  }

}
