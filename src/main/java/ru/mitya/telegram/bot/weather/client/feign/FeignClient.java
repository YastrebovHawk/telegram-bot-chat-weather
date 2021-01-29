package ru.mitya.telegram.bot.weather.client.feign;

import feign.Param;
import feign.RequestLine;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.client.model.WeatherModel;

public interface FeignClient extends WeatherClient {

    @RequestLine("GET /data/2.5/weather?q={city}&units=metric&appid=${api.openweathermap.key}}")
    WeatherModel getWeatherModels(@Param("city") String city);

}
