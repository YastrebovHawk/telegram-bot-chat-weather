package ru.mitya.telegram.bot.weather.command.notifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class NotifierCommandValidatorTest {

    private NotifierCommandValidator uut;

    @BeforeEach
    void setUp() {
        uut = new NotifierCommandValidator();
    }

    @Test
    @DisplayName("Тест проверяет конвертацию запроса на получение списка всех подписок")
    void shouldConvertArgumentsForGettingAllSubscriptions() throws Exception {
        final NotifierCommandAction actualAction = uut.convertArguments(new String[]{});

        Assertions.assertNotNull(actualAction, "ответ валидатора не может быть null");
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertEquals(actualAction.getNotifierCommandMode(), NotifierCommandMode.ALL_SUBSCRIPTIONS_FOR_USER);
    }

    @Test
    @DisplayName("Тест проверяет обработку исключения NullPointerException")
    void shouldHandleNullArgument() {
        final NotifierCommandAction actualAction = uut.convertArguments(null);
        Assertions.assertNotNull(actualAction);
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getNotifierCommandMode(), NotifierCommandMode.ALL_SUBSCRIPTIONS_FOR_USER);
    }

    @Test
    @DisplayName("Тест проверяет обработку аргумента, где отсутсвует время")
    void shouldHandleTimeMissingError(){
        final NotifierCommandAction actualAction = uut.convertArguments(new String[]{"london"});
        Assertions.assertNotNull(actualAction);
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getNotifierCommandMode(), NotifierCommandMode.TIME_MISSING_ERROR);
    }

    @Test
    @DisplayName("Тест проверяет обработку аргументов, где неправильно введен формат времени")
    void shouldHandleIncorrectTimeFormatError() {
        final NotifierCommandAction actualAction = uut.convertArguments(new String[]{"london", "10 00"});
        Assertions.assertNotNull(actualAction);
        Assertions.assertNull(actualAction.getTime());
        Assertions.assertNull(actualAction.getCity());
        Assertions.assertEquals(actualAction.getNotifierCommandMode(), NotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR);
    }

    @Test
    @DisplayName("Тест проверяет обработку аргументов для сохранения подписки")
    void  shouldConvertArgumentsForSaveSubscription(){
        final NotifierCommandAction actualAction = uut.convertArguments(new String[]{"10:00", "Ростов", "на", "Дону"});
        Assertions.assertNotNull(actualAction);
        Assertions.assertEquals(actualAction.getTime(), LocalTime.parse("10:00"));
        Assertions.assertEquals(actualAction.getCity(), "Ростов на Дону");
        Assertions.assertEquals(actualAction.getNotifierCommandMode(), NotifierCommandMode.SAVE_SUBSCRIPTION);
    }

}