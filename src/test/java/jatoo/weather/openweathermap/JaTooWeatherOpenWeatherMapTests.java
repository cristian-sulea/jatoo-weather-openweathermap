package jatoo.weather.openweathermap;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import jatoo.weather.JaTooWeather;
import jatoo.weather.JaTooWeatherService;

public class JaTooWeatherOpenWeatherMapTests {

  private static final String APPID = "0b438dbe7fb5d24ce8ca273834fc988c";
  private static final String CITY = "683506";

  @Test
  public void test1() throws Throwable {

    JaTooWeatherService service = new JaTooWeatherOpenWeatherMap(APPID) {
      protected String getResponse(String url) throws IOException {
        return IOUtils.toString(JaTooWeatherOpenWeatherMapTests.class.getResource("response1.json"));
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

    JaTooWeatherService service = new JaTooWeatherOpenWeatherMap(APPID) {
      protected String getResponse(String url) throws IOException {
        return IOUtils.toString(JaTooWeatherOpenWeatherMapTests.class.getResource("response2.json"));
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
