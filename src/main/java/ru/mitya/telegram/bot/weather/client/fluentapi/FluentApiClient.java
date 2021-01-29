package ru.mitya.telegram.bot.weather.client.fluentapi;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.client.model.WeatherModel;

import java.io.IOException;

@Slf4j
@Component
public class FluentApiClient implements WeatherClient {

    private final String key;
    private final String url;
    private final Gson gson;

    @Autowired
    public FluentApiClient(@Value("${api.openweathermap.key}")String key,
                           @Value("${api.openweathermap.baseUrl}${api.openweathermap.path}")String url,
                           Gson gson) {
        this.key = key;
        this.url = url;
        this.gson = gson;
    }

    public WeatherModel getWeatherModels(String city) {
        WeatherModel model = new WeatherModel();
        try {
            String stringJson = Request.Get(url + "?q=" + city + "&units=metric&appid=" + key).execute().returnContent().asString();
            model = gson.fromJson(stringJson, WeatherModel.class);
            log.debug("Преобразование строки json {} в модель WeatherModel", stringJson);
        } catch (IOException e) {
            log.error("Произошла ошибка, при отправке Get запроса", e);
        }
        return model;
    }

}
