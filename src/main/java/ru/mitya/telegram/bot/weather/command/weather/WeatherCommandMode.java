package ru.mitya.telegram.bot.weather.command.weather;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.io.IOException;

enum WeatherCommandMode {

    WEATHER_IN_THE_CITY{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat, WeatherTelegramHelper notifierTelegramHelper, WeatherCommandParams commandParams) throws TelegramApiException, IOException {
            String city = commandParams.getCity();
            notifierTelegramHelper.sendMessageWeatherInTheCity(botProxy, user, city);
        }
    },
    INPUT_FORMAT_ERROR{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat, WeatherTelegramHelper notifierTelegramHelper, WeatherCommandParams commandParams) throws TelegramApiException, IOException {
            notifierTelegramHelper.handleInputFormatError(botProxy, user);
        }
    };

    abstract void executeAction(BotProxy botProxy, User user, Chat chat,
                                WeatherTelegramHelper notifierTelegramHelper,
                                WeatherCommandParams commandParams) throws TelegramApiException, IOException;

}
