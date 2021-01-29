package ru.mitya.telegram.bot.weather.command.weather;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotCommands;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.command.WeatherBotCommand;

import java.io.IOException;
import java.util.ResourceBundle;


@Controller
@Slf4j
public class WeatherCommand extends WeatherBotCommand {

    private final WeatherTelegramHelper weatherTelegramHelper;

    @Autowired
    public WeatherCommand(ResourceBundle resourceBundle, WeatherTelegramHelper weatherTelegramHelper) {
        super(BotCommands.WEATHER.getIdentifier(), resourceBundle.getString("lang.command.weather.description.extended"));
        this.weatherTelegramHelper = weatherTelegramHelper;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        final WeatherCommandAction action = WeatherCommandValidator.convertArguments(arguments);
        final BotProxy botProxy = BotProxy.create(absSender);
        try {
            log.info("Пришла команда {} со  списком аргументов {} от юзера {}. Поток - {}",
                    WeatherCommand.class.toString(), arguments, chat.getId(), Thread.currentThread());
           action.getWeatherCommandMode().executeAction(botProxy, user, chat, weatherTelegramHelper, action);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка {} при отправке ответа юзеру {} с потока - {}: {}", e, chat.getId(), Thread.currentThread(), e.getStackTrace());
        } catch (IOException e) {
            log.error("Разрыв связи {} при отправке ответа юзеру {} с потока - {}: {}", e, chat.getId(), Thread.currentThread(), e.getStackTrace());
        }
    }

}
