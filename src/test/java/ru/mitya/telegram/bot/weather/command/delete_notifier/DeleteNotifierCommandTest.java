package ru.mitya.telegram.bot.weather.command.delete_notifier;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class DeleteNotifierCommandTest {

    private DeleteNotifierCommand uut;
    private final AbsSender absSender = mock(AbsSender.class);
    private final User user = mock(User.class);
    private final Chat chat = mock(Chat.class);
    private final String[] strings = new String[]{};
    private final DeleteNotifierTelegramHelper deleteNotifierTelegramHelper = mock(DeleteNotifierTelegramHelper.class);
    private final DeleteNotifierCommandValidator deleteNotifierCommandValidator = mock(DeleteNotifierCommandValidator.class);
    private final ResourceBundle resourceBundle = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String s) {
            return "Удаляет уведомление: /delete [time] [city]";
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    @BeforeEach
    void setUp() {
        uut = new DeleteNotifierCommand(resourceBundle, deleteNotifierTelegramHelper, deleteNotifierCommandValidator);
    }

    @Test
    @DisplayName("Тест проверяет работу метода при исключение NullPointException")
    void shouldExecuteHandleNullPointException() throws Exception {

        final DeleteNotifierCommandAction action = mock(DeleteNotifierCommandAction.class);
        when(deleteNotifierCommandValidator.convertArguments(any())).thenReturn(action);

        final DeleteNotifierCommandMode commandMode = mock(DeleteNotifierCommandMode.class);
        when(action.getDeleteNotifierCommandMode()).thenReturn(commandMode);

        doThrow(new NullPointerException()).when(commandMode).executeAction(any(), any(), any(), any(), any());
        Assertions.assertThrows(NullPointerException.class, () -> uut.execute(absSender, user, chat, strings));

        verify(deleteNotifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(deleteNotifierTelegramHelper), eq(action));
    }

    @Test
    @DisplayName("Тест проверяет удаление подписки")
    void shouldExecuteDelete() throws Exception {

        final DeleteNotifierCommandAction action = mock(DeleteNotifierCommandAction.class);
        when(deleteNotifierCommandValidator.convertArguments(any())).thenReturn(action);

        final DeleteNotifierCommandMode commandMode = mock(DeleteNotifierCommandMode.class);
        when(action.getDeleteNotifierCommandMode()).thenReturn(commandMode);

        uut.execute(absSender, user, chat, strings);

        verify(deleteNotifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(deleteNotifierTelegramHelper), eq(action));
    }

    @Test
    @DisplayName("Тест проверяет работу метода при исключение TelegramApiException")
    void shouldExecuteHandleTelegramApiException() throws Exception {

        final DeleteNotifierCommandAction action = mock(DeleteNotifierCommandAction.class);
        when(deleteNotifierCommandValidator.convertArguments(any())).thenReturn(action);

        final DeleteNotifierCommandMode commandMode = mock(DeleteNotifierCommandMode.class);
        when(action.getDeleteNotifierCommandMode()).thenReturn(commandMode);

        doThrow(TelegramApiException.class).when(commandMode).executeAction(any(), any(), any(), any(), any());
        uut.execute(absSender, user, chat, strings);

        verify(deleteNotifierCommandValidator).convertArguments(eq(strings));
        verify(commandMode).executeAction(notNull(), same(user), same(chat), same(deleteNotifierTelegramHelper), eq(action));
    }

}