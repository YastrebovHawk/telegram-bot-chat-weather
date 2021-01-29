package ru.mitya.telegram.bot.weather.command.delete_notifier;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.time.LocalTime;

enum DeleteNotifierCommandMode {

    DELETE_SUBSCRIPTION_FOR_USER{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat,
                           DeleteNotifierTelegramHelper notifierTelegramHelper,
                           DeleteNotifierCommandParams commandParams) throws TelegramApiException {
            LocalTime time = commandParams.getTime();
            String city = commandParams.getCity();
            notifierTelegramHelper.deleteSubscriptionForUser(botProxy, user, time, city);
        }
    },
    INCORRECT_TIME_FORMAT_ERROR{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat,
                           DeleteNotifierTelegramHelper notifierTelegramHelper,
                           DeleteNotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.handleIncorrectTimeFormatError(botProxy, user);
        }
    },
    INPUT_FORMAT_ERROR{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat,
                           DeleteNotifierTelegramHelper notifierTelegramHelper,
                           DeleteNotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.handleInputFormatError(botProxy, user);
        }
    };

    abstract void executeAction(BotProxy botProxy, User user, Chat chat,
                                DeleteNotifierTelegramHelper notifierTelegramHelper,
                                DeleteNotifierCommandParams commandParams) throws TelegramApiException;

}
