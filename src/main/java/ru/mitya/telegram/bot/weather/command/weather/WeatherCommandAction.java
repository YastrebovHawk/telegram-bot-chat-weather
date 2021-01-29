package ru.mitya.telegram.bot.weather.command.weather;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class WeatherCommandAction implements WeatherCommandParams {

    private final String city;
    private final WeatherCommandMode weatherCommandMode;

    public static WeatherCommandAction createWeatherInTheCity(String city){
        return new WeatherCommandAction(city, WeatherCommandMode.WEATHER_IN_THE_CITY);
    }

    public static WeatherCommandAction createInputFormatError(){
        return new WeatherCommandAction(null, WeatherCommandMode.INPUT_FORMAT_ERROR);
    }

}
