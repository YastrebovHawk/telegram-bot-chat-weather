package ru.mitya.telegram.bot.weather.command.delete_notifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class DeleteNotifierCommandValidatorTest {

    private DeleteNotifierCommandValidator uut;

    @BeforeEach
    void setUp() {
        uut = new DeleteNotifierCommandValidator();
    }

    @Test
    @DisplayName("Тест проверяет обработку неправельно введенного формата запроса")
    void shouldHandleInputFormatError() {
        final DeleteNotifierCommandAction actualAction = uut.convertArguments(new String[]{});
        Assertions.assertNotNull(actualAction, "ответ валидатора не может быть null");
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getDeleteNotifierCommandMode(), DeleteNotifierCommandMode.INPUT_FORMAT_ERROR);
    }

    @Test
    @DisplayName("Тест проверяет обработку неправильно введенного формата времение")
    void shouldHandleIncorrectTimeFormatError() {
        final DeleteNotifierCommandAction actualAction = uut.convertArguments(new String[]{"10000", "london"});
        Assertions.assertNotNull(actualAction);
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getDeleteNotifierCommandMode(), DeleteNotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR);
    }

    @Test
    @DisplayName("Тест проверяет реализацию удаления подписки")
    void shouldHandleDeleteSubscriptionForUser() {
        final DeleteNotifierCommandAction actualAction = uut.convertArguments(new String[]{"10:20", "london"});
        Assertions.assertNotNull(actualAction);
        Assertions.assertEquals(actualAction.getTime(), LocalTime.parse("10:20"));
        Assertions.assertEquals(actualAction.getCity(), "london");
        Assertions.assertEquals(actualAction.getDeleteNotifierCommandMode(), DeleteNotifierCommandMode.DELETE_SUBSCRIPTION_FOR_USER);
    }

    @Test
    @DisplayName("Тест проверяет обработку исключения NullPointerException")
    void shouldHandle() {
        final DeleteNotifierCommandAction actualAction = uut.convertArguments(null);
        Assertions.assertNotNull(actualAction);
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getDeleteNotifierCommandMode(), DeleteNotifierCommandMode.INPUT_FORMAT_ERROR);
    }

}