package ru.mitya.telegram.bot.weather.command.notifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;
import ru.mitya.telegram.bot.weather.jpa.service.SubscriptionService;

import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.*;

class NotifierTelegramHelperTest {

    private final User user = mock(User.class);
    private final BotProxy botProxy = mock(BotProxy.class);
    private final HashMap<String, String> hashMapResource = new HashMap<>();
    private final SubscriptionService subscriptionService = mock(SubscriptionService.class);
    private final ResourceBundle resourceBundle = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String s) {
            return hashMapResource.get(s);
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    };

    private NotifierTelegramHelper uut;

    @BeforeEach
    void setUp() {
        uut = new NotifierTelegramHelper(subscriptionService, resourceBundle);
        hashMapResource.put("lang.command.notifier.description.extended",
                "Добавляет уведомления в определенное время: /notifier [hh:mm] [city]. Показывает список уведомлений: /notifier");
        hashMapResource.put("lang.command.notifier.message.notifier",
                "Уведомление уже существует");
        hashMapResource.put("lang.command.notifier.message.format.time",
                "Введите правильный формат времени [hh:mm]");
        hashMapResource.put("lang.command.notifier.message.nullformat",
                "Произошла ошибка, повторите запрос");
        hashMapResource.put("lang.command.notifier.message.format", "Введите команду /notifier [hh:mm] [city]");
    }

    @Test
    @DisplayName("Тест проверяет отправление ответа юзеру при не пустом списке подписок")
    void shouldSendListOfSubscriptions() throws Exception {
        BotProxy botProxy = mock(BotProxy.class);
        Subscription subscription = mock(Subscription.class);
        List<Subscription> list = new ArrayList<>();
        list.add(subscription);
        when(subscriptionService.reviewNotifier(user.getId())).thenReturn(list);

        uut.sendListOfSubscriptions(botProxy, user);
        StringBuilder sb = new StringBuilder();
        list.forEach((b) -> sb.append(b.toString()));
        SendMessage sendMessage = new SendMessage(user.getId().toString(), sb.toString());
        verify(botProxy).execute(sendMessage);
    }

    @Test
    @DisplayName("Тест проверяет отправление ответа юзеру при пустом списке подписок")
    void shouldSendEmptyListOfSubscription() throws Exception {
        List<Subscription> list = new ArrayList<>();
        when(subscriptionService.reviewNotifier(user.getId())).thenReturn(list);

        uut.sendListOfSubscriptions(botProxy, user);
        SendMessage sendMessage = new SendMessage(user.getId().toString(), "Уведомлений нет");
        verify(botProxy).execute(sendMessage);
    }

    @Test
    @DisplayName("Тест проверяет не сохранение существующей подписки")
    void shouldSaveExistingSubscription() throws Exception {
        LocalTime time = LocalTime.parse("10:10");
        when(subscriptionService.save(user, time, "london")).thenReturn(false);

        uut.saveSubscription(botProxy, user, time, "london");
        SendMessage sendMessage = new SendMessage(user.getId().toString(), "Уведомление уже существует");
        verify(botProxy).execute(sendMessage);
    }

    @Test
    @DisplayName("Тест проверяет отправку юзеру сообщения о некорректном вводе формата времени")
    void shouldHandleIncorrectTimeFormatError() throws Exception {
        uut.handleIncorrectTimeFormatError(botProxy, user);

        SendMessage sendMessage = new SendMessage(user.getId().toString(), "Введите правильный формат времени [hh:mm]");
        verify(botProxy).execute(sendMessage);
    }

    @Test
    void shouldHandleMissingTimeError() throws Exception {
        uut.handleMissingTimeError(botProxy, user);

        SendMessage sendMessage = new SendMessage(user.getId().toString(), "Введите команду /notifier [hh:mm] [city]");
        verify(botProxy).execute(sendMessage);
    }

    @Test
    void shouldHandleInputFormatError() throws Exception {
        uut.handleInputFormatError(botProxy, user);

        SendMessage sendMessage = new SendMessage(user.getId().toString(), "Произошла ошибка, повторите запрос");
        verify(botProxy).execute(sendMessage);
    }

}