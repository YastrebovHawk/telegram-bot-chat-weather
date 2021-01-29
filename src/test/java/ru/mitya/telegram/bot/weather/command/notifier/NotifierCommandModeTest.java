package ru.mitya.telegram.bot.weather.command.notifier;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotifierCommandModeTest {

    private final User user = mock(User.class);
    private final BotProxy botProxy = mock(BotProxy.class);
    private final Chat chat = mock(Chat.class);
    private final NotifierTelegramHelper notifierTelegramHelper = mock(NotifierTelegramHelper.class);

    @Test
    void shouldSendAllSubscriptionForUser() throws Exception{
        final NotifierCommandParams notifierCommandParams = NotifierCommandAction.createListOfAllSubscriptionsAction();
        NotifierCommandMode.ALL_SUBSCRIPTIONS_FOR_USER.executeAction(botProxy, user, chat, notifierTelegramHelper, notifierCommandParams);

        verify(notifierTelegramHelper).sendListOfSubscriptions(botProxy, user);
    }

    @Test
    void shouldSaveSubscription() throws Exception{
        final NotifierCommandParams notifierCommandParams = NotifierCommandAction.createSaveSubscriptionAction(LocalTime.parse("10:10"), "london");
        NotifierCommandMode.SAVE_SUBSCRIPTION.executeAction(botProxy, user, chat, notifierTelegramHelper, notifierCommandParams);
        LocalTime localTime = notifierCommandParams.getTime();
        String city = notifierCommandParams.getCity();
        verify(notifierTelegramHelper).saveSubscription(botProxy, user, localTime, city);
    }

    @Test
    void shouldHandleIncorrectTimeFormatError() throws Exception {
        final  NotifierCommandParams notifierCommandParams = NotifierCommandAction.createIncorrectTimeFormatError();
        NotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR.executeAction(botProxy, user, chat, notifierTelegramHelper, notifierCommandParams);

        verify(notifierTelegramHelper).handleIncorrectTimeFormatError(botProxy, user);
    }

    @Test
    void shouldHandleMissingTimeError() throws Exception {
        final NotifierCommandParams notifierCommandParams = NotifierCommandAction.createTimeMissingError();
        NotifierCommandMode.TIME_MISSING_ERROR.executeAction(botProxy, user, chat, notifierTelegramHelper, notifierCommandParams);

        verify(notifierTelegramHelper).handleMissingTimeError(botProxy, user);
    }

    @Test
    void shouldHandleInputFormatError() throws Exception {
        final  NotifierCommandParams notifierCommandParams = NotifierCommandAction.createInputFormatError();
        NotifierCommandMode.INPUT_FORMAT_ERROR.executeAction(botProxy, user, chat, notifierTelegramHelper, notifierCommandParams);

        verify(notifierTelegramHelper).handleInputFormatError(botProxy, user);
    }

}