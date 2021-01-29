package ru.mitya.telegram.bot.weather.command.delete_notifier;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.command.BotProxy;

import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteNotifierCommandModeTest {

    private final User user = mock(User.class);
    private final BotProxy botProxy = mock(BotProxy.class);
    private final Chat chat = mock(Chat.class);
    private final DeleteNotifierTelegramHelper deleteNotifierTelegramHelper = mock(DeleteNotifierTelegramHelper.class);

    @Test
    void shouldDeleteSubscription() throws Exception {
        final DeleteNotifierCommandParams deleteNotifierCommandParams = DeleteNotifierCommandAction.
                createDeleteSubscriptionForUser(LocalTime.parse("10:20"), "london");
        DeleteNotifierCommandMode.DELETE_SUBSCRIPTION_FOR_USER.executeAction(botProxy, user, chat, deleteNotifierTelegramHelper, deleteNotifierCommandParams);
        LocalTime localTime = deleteNotifierCommandParams.getTime();
        String city = deleteNotifierCommandParams.getCity();
        verify(deleteNotifierTelegramHelper).deleteSubscriptionForUser(botProxy, user, localTime, city);
    }

    @Test
    void shouldInputFormatError() throws Exception {
        final DeleteNotifierCommandParams deleteNotifierCommandParams = DeleteNotifierCommandAction.
                createInputFormatError();
        DeleteNotifierCommandMode.INPUT_FORMAT_ERROR.executeAction(botProxy, user, chat, deleteNotifierTelegramHelper, deleteNotifierCommandParams);
        verify(deleteNotifierTelegramHelper).handleInputFormatError(botProxy, user);
    }

    @Test
    void shouldIncorrectTimeFormatError() throws Exception {
        final DeleteNotifierCommandParams deleteNotifierCommandParams = DeleteNotifierCommandAction.
                createIncorrectTimeFormatError();
        DeleteNotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR.executeAction(botProxy, user, chat, deleteNotifierTelegramHelper, deleteNotifierCommandParams);
        verify(deleteNotifierTelegramHelper).handleIncorrectTimeFormatError(botProxy, user);
    }

}