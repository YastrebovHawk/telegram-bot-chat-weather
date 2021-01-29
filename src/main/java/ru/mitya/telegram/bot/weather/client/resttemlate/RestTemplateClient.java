package ru.mitya.telegram.bot.weather.client.resttemlate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.client.model.WeatherModel;

import java.util.HashMap;

@Component
@Primary
public class RestTemplateClient implements WeatherClient {

    private final String key;
    private final String url;
    @Autowired
    private final RestTemplate restTemplate;

    public RestTemplateClient(@Value("${api.openweathermap.key}")String key,
                              @Value("${api.openweathermap.baseUrl}${api.openweathermap.path}${api.openweathermap.template.queryParameters}") String url,
                              RestTemplate restTemplate) {
        this.key = key;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public WeatherModel getWeatherModels(String city){
        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("city", city);
        queryParameters.put("metric", "metric");
        queryParameters.put("key", key);
        return restTemplate.getForObject(url,  WeatherModel.class, queryParameters);
    }

}
