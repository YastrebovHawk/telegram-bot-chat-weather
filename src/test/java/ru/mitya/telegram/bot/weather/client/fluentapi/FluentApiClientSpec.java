package ru.mitya.telegram.bot.weather.client.fluentapi;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.client.WeatherClientSpec;
import ru.mitya.telegram.bot.weather.configuration.ClientConfiguration;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

@ContextConfiguration(classes = ClientConfiguration.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class FluentApiClientSpec extends WeatherClientSpec {

    private static MockServerClient mockServer;
    @Value("${api.openweathermap.key}")
    private String key;

    @Autowired
    private Gson gson;

    @BeforeEach
    public void startServer() {
        mockServer = startClientAndServer(8080);
        createWeatherRequesterInTheCity();
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }

    @Override
    public WeatherClient getUUT() {
        return new FluentApiClient(key,"http://127.0.0.1:8080/data/2.5/weather", gson);
    }
    public void createWeatherRequesterInTheCity() {
        mockServer.when(request()
                .withMethod("GET")
                .withPath("/data/2.5/weather")
                .withQueryStringParameters(Parameter.param("q", "london"),
                        Parameter.param("units", "metric"), Parameter.param("appid", key))
        )
                .respond(HttpResponse.response()
                        .withBody(new JsonBody("{'weather': [{'main': 'Rain', 'icon': '10d'}], 'main': {'temp': 1.43, 'humidity': 93}, 'wind': {'speed':" +
                                " 3.6}, 'name': 'london'}"))
                        .withHeader(new Header("Content-type", "application/json", "charset=utf-8")));

    }

}