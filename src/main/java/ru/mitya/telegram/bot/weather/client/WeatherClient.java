package ru.mitya.telegram.bot.weather.client;

import ru.mitya.telegram.bot.weather.client.model.WeatherModel;

public interface WeatherClient {

    WeatherModel getWeatherModels(String city);

}
