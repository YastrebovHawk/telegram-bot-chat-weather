package ru.mitya.telegram.bot.weather.command.weather;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.client.Converter;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.io.IOException;
import java.util.ResourceBundle;

@Service
@Slf4j
@AllArgsConstructor
class WeatherTelegramHelper {

    private final WeatherClient weatherClient;
    private final ResourceBundle resourceBundle;

    void sendMessageWeatherInTheCity(BotProxy botProxy, User user, String city) throws TelegramApiException, IOException {
        log.debug("Отправлен 1 аргумент {} юзером {}", city, user.getId());
        botProxy.execute(new SendMessage(user.getId().toString(), Converter.getTextWeather(weatherClient.getWeatherModels(city))));
        botProxy.execute(Converter.getIconWeather(weatherClient.getWeatherModels(city)).setChatId(user.getId().toString()));
    }

    void handleInputFormatError(BotProxy botProxy, User user) throws TelegramApiException{
        log.warn("Введен неправиьный формат аргументов юзера {}", user.getId());
        botProxy.execute(new SendMessage(user.getId().toString(), resourceBundle.getString("lang.command.weather.message")));
    }

}
