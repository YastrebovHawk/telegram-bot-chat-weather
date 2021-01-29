package ru.mitya.telegram.bot.weather.client.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherModel {

    private List<ObjectWeather> weather;
    private Main main;
    private Wind wind;
    private String name;

}


