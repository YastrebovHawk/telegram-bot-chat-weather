package ru.mitya.telegram.bot.weather.command.notifier;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotCommands;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.command.WeatherBotCommand;

import java.util.ResourceBundle;

@Component
@Slf4j
public class NotifierCommand extends WeatherBotCommand {

    private final NotifierTelegramHelper notifierTelegramHelper;
    private final NotifierCommandValidator notifierCommandValidator;

    @Autowired
    public NotifierCommand(ResourceBundle resourceBundle,  NotifierTelegramHelper notifierTelegramHelper, NotifierCommandValidator notifierCommandValidator
    ) {
        super(BotCommands.NOTIFIER.getIdentifier(), resourceBundle.getString("lang.command.notifier.description.extended"));
        this.notifierTelegramHelper = notifierTelegramHelper;
        this.notifierCommandValidator = notifierCommandValidator;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Пришла команда {} со  списком аргументов {} от юзера {}", NotifierCommand.class.toString(), arguments, chat.getId());
        final NotifierCommandAction action = notifierCommandValidator.convertArguments(arguments);
        final BotProxy botProxy = BotProxy.create(absSender);
        try {
            action.getNotifierCommandMode()
                    .executeAction(botProxy, user, chat, notifierTelegramHelper, action);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка {} при отправке ответа юзеру {}: {}", e, chat.getId(), e.getStackTrace());
        }
    }

}