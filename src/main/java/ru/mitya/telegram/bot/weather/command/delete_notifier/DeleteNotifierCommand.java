package ru.mitya.telegram.bot.weather.command.delete_notifier;

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

@Slf4j
@Component
public class DeleteNotifierCommand extends WeatherBotCommand {

    private final DeleteNotifierTelegramHelper deleteNotifierTelegramHelper;
    private final DeleteNotifierCommandValidator deleteNotifierCommandValidator;

    @Autowired
    public DeleteNotifierCommand(ResourceBundle resourceBundle,
                                 DeleteNotifierTelegramHelper deleteNotifierTelegramHelper,
                                 DeleteNotifierCommandValidator deleteNotifierCommandValidator) {
        super(BotCommands.DELETE.getIdentifier(), resourceBundle.getString("lang.command.delete.notifier.description.extended"));
        this.deleteNotifierTelegramHelper = deleteNotifierTelegramHelper;
        this.deleteNotifierCommandValidator = deleteNotifierCommandValidator;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        final DeleteNotifierCommandAction action =deleteNotifierCommandValidator.convertArguments(arguments);
        final BotProxy botProxy = BotProxy.create(absSender);
        try {
            log.info("Пришла команда {} со  списком аргументов {} от юзера {}", DeleteNotifierCommand.class.toString(), arguments, chat.getId());
            action.getDeleteNotifierCommandMode().executeAction(botProxy, user, chat, deleteNotifierTelegramHelper, action);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка {} при отправке ответа юзеру {}: {}", e, chat.getId(), e.getStackTrace());
        }
    }

}
