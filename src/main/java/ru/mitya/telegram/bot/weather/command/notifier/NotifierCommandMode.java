package ru.mitya.telegram.bot.weather.command.notifier;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.time.LocalTime;

enum NotifierCommandMode {

    ALL_SUBSCRIPTIONS_FOR_USER {
        @Override
        public void executeAction(BotProxy botProxy, User user, Chat chat, NotifierTelegramHelper notifierTelegramHelper,
                                  NotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.sendListOfSubscriptions(botProxy, user);
        }
    },
    SAVE_SUBSCRIPTION {
        @Override
        public void executeAction(BotProxy botProxy, User user, Chat chat,
                                  NotifierTelegramHelper notifierTelegramHelper,
                                  NotifierCommandParams commandParams) throws TelegramApiException {
            LocalTime time = commandParams.getTime();
            String city = commandParams.getCity();
            notifierTelegramHelper.saveSubscription(botProxy, user, time, city);
        }
    },
    INCORRECT_TIME_FORMAT_ERROR {
        @Override
        public void executeAction(BotProxy botProxy, User user, Chat chat,
                                  NotifierTelegramHelper notifierTelegramHelper, NotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.handleIncorrectTimeFormatError(botProxy, user);
        }
    },
    TIME_MISSING_ERROR {
        @Override
        public void executeAction(BotProxy botProxy, User user, Chat chat,
                                  NotifierTelegramHelper notifierTelegramHelper, NotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.handleMissingTimeError(botProxy, user);
        }
    },
    INPUT_FORMAT_ERROR{
        @Override
        void executeAction(BotProxy botProxy, User user, Chat chat, NotifierTelegramHelper notifierTelegramHelper, NotifierCommandParams commandParams) throws TelegramApiException {
            notifierTelegramHelper.handleInputFormatError(botProxy, user);
        }
    };

    abstract void executeAction(BotProxy botProxy, User user, Chat chat,
                                NotifierTelegramHelper notifierTelegramHelper,
                                NotifierCommandParams commandParams) throws TelegramApiException;

}