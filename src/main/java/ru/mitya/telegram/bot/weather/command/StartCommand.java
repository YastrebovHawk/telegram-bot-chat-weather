package ru.mitya.telegram.bot.weather.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ResourceBundle;

@Slf4j
@Component
public class StartCommand extends WeatherBotCommand {

    private final ResourceBundle resourceBundle;

    public StartCommand(@Autowired ResourceBundle resourceBundle) {
        super(BotCommands.START.getIdentifier(), resourceBundle.getString("lang.command.start.description.extended"));
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        try{
            log.info("Пришла команда {} со  списком аргументов {} от юзера {}", StartCommand.class.toString(), arguments, chat.getId());
            setMessageText(absSender, user, resourceBundle.getString("lang.command.start.message"));
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка {} при отправке ответа юзеру {}: {}", e, chat.getId(), e.getStackTrace());
        }
    }

}
