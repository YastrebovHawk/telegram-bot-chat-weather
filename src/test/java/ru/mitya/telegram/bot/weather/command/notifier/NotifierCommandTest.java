package ru.mitya.telegram.bot.weather.command.notifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotifierCommandTest {

    private NotifierCommand uut;
    private final AbsSender absSender = mock(AbsSender.class);
    private final User user = mock(User.class);
    private final Chat chat = mock(Chat.class);
    private final String[] strings = new String[]{};
    private final NotifierTelegramHelper notifierTelegramHelper = mock(NotifierTelegramHelper.class);
    private final NotifierCommandValidator notifierCommandValidator = mock(NotifierCommandValidator.class);
    private final ResourceBundle resourceBundle = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String s) {
            return "Добавляет уведомления в определенное время: /notifier [hh:mm] [city]. Показывает список уведомлений: /notifier";
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    @BeforeEach
    void setUp() {
        uut = new NotifierCommand(resourceBundle, notifierTelegramHelper, notifierCommandValidator);
    }

    @Test
    @DisplayName("Тест проверяет работу метода при исключение NullPointException")
    void shouldExecuteHandleNullPointException() throws Exception {

        final NotifierCommandAction action = mock(NotifierCommandAction.class);
        when(notifierCommandValidator.convertArguments(any())).thenReturn(action);

        final NotifierCommandMode commandMode = mock(NotifierCommandMode.class);
        when(action.getNotifierCommandMode()).thenReturn(commandMode);

        doThrow(new NullPointerException()).when(commandMode).executeAction(any(), any(), any(), any(), any());
        Assertions.assertThrows(NullPointerException.class, () -> uut.execute(absSender, user, chat, strings));

        verify(notifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(notifierTelegramHelper), eq(action));
    }

    @Test
    @DisplayName("Тест проверяет работу метода в случае успеха")
    void shouldExecuteSuccess() throws Exception {
        final NotifierCommandAction action = mock(NotifierCommandAction.class);
        when(notifierCommandValidator.convertArguments(any())).thenReturn(action);

        final NotifierCommandMode commandMode = mock(NotifierCommandMode.class);
        when(action.getNotifierCommandMode()).thenReturn(commandMode);

        uut.execute(absSender, user, chat, strings);

        verify(notifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(notifierTelegramHelper), eq(action));
    }

    @Test
    @DisplayName("Тест проверяет работу метода при исключение TelegramApiException")
    void shouldExecuteHandleTelegramApiException() throws Exception {
        final NotifierCommandAction action = mock(NotifierCommandAction.class);
        when(notifierCommandValidator.convertArguments(any())).thenReturn(action);

        final NotifierCommandMode commandMode = mock(NotifierCommandMode.class);
        when(action.getNotifierCommandMode()).thenReturn(commandMode);

        doThrow(TelegramApiException.class).when(commandMode).executeAction(any(), any(), any(), any(), any());
        uut.execute(absSender, user, chat, strings);

        verify(notifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(notifierTelegramHelper), eq(action));
    }

}