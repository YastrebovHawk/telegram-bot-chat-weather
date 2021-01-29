package ru.mitya.telegram.bot.weather.client;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.mitya.telegram.bot.weather.client.model.WeatherModel;

public class Converter {

    public static String getTextWeather(WeatherModel model) {
        if (model != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("City: ").append(model.getName())
                    .append("\n").append("Temperature: ")
                    .append(model.getMain().getTemp()).append(" C\n")
                    .append("Speed wind: ").append(model.getWind().getSpeed())
                    .append(" m/s\n").append(model.getWeather().get(0).getMain());
            return sb.toString();
        } else return "Введите правильно параметр";
    }

    public static SendPhoto getIconWeather(WeatherModel model) {
        if (model != null) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto("http://openweathermap.org/img/wn/"
                    + model.getWeather().get(0).getIcon() + "@2x.png");
            return sendPhoto;
        } else return null;
    }

}
