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

public class JaTooWeatherOpenWeatherMap extends JaTooWeatherService {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooWeatherOpenWeatherMap.class);

  private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/";
  private static final String PATH_CURRENT = "weather";
  private static final String PATH_FORECAST = "forecast/daily";

  private final String appid;

  public JaTooWeatherOpenWeatherMap(final String language, final String appid) {
    super(language);
    this.appid = appid;
  }

  public JaTooWeatherOpenWeatherMap(final String appid) {
    super();
    this.appid = appid;
  }

  @Override
  public JaTooWeather getWeather(final String city) throws IOException {

    String url = URL_BASE + PATH_CURRENT + "?appid=" + appid + "&id=" + city + "&units=metric";

    CloseableHttpClient client = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpGet request = new HttpGet(url);
    CloseableHttpResponse response = client.execute(request);
    BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());

    JSONObject json = new JSONObject(EntityUtils.toString(entity, "UTF-8"));

    System.out.println(json.toString(2));

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

  public static void main(String[] args) throws Throwable {

    JaTooWeatherService service = new JaTooWeatherOpenWeatherMap("0b438dbe7fb5d24ce8ca273834fc988c");
    JaTooWeather weather = service.getWeather("683506");

    System.out.println(service.getDescription(weather));
    System.out.println(service.getTemperatureWithText(weather));
    System.out.println(service.getHumidityWithText(weather));
    System.out.println(service.getPressureWithText(weather));
    System.out.println(service.getWindWithText(weather));
    System.out.println(service.getWindDirectionWithText(weather));
    System.out.println(service.getCloudsWithText(weather));
    System.out.println(service.getRainWithText(weather));
    System.out.println(service.getSnowWithText(weather));
    System.out.println(service.getSunriseWithText(weather));
    System.out.println(service.getSunsetWithText(weather));

  }

}
