package ru.mitya.telegram.bot.weather.notice;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.jpa.entity.WeatherUser;

import java.util.ArrayList;

public class BuilderUser {

    public static WeatherUser createWeatherUser(User user){
        return new WeatherUser(user.getId(), user.getFirstName(), user.getBot(),
                user.getLastName(), user.getUserName(), user.getLanguageCode(), new ArrayList<>());

    }

    public static User createUser(WeatherUser weatherUser){
        return new User(weatherUser.getChatId(), weatherUser.getFirstName(), weatherUser.getIsBot(),
                weatherUser.getLastName(), weatherUser.getUserName(), weatherUser.getLanguageCode());
    }

}
