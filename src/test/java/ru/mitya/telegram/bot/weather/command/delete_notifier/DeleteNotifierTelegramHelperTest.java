package ru.mitya.telegram.bot.weather.command.delete_notifier;

import org.junit.jupiter.api.BeforeEach;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.jpa.service.SubscriptionService;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import static org.mockito.Mockito.mock;

class DeleteNotifierTelegramHelperTest {

    private DeleteNotifierTelegramHelper uut;
    private final User user = mock(User.class);
    private final BotProxy botProxy = mock(BotProxy.class);
    private HashMap<String, String> hashMapResource = new HashMap<>();
    private final SubscriptionService subscriptionService = mock(SubscriptionService.class);
    private final WeatherClient weatherClient = mock(WeatherClient.class);
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

    @BeforeEach
    void setUp() {
        uut = new DeleteNotifierTelegramHelper(subscriptionService, resourceBundle);
        hashMapResource.put("lang.command.delete.notifier.message.delete", "Уведомление удалено");
        hashMapResource.put("lang.command.delete.notifier.message.!notifier", "Уведомления не существует");
        hashMapResource.put("lang.command.notifier.message.format.time",
                "Введите правильный формат времени [hh:mm]");
        hashMapResource.put("lang.command.notifier.message.nullformat",
                "Произошла ошибка, повторите запрос");
        hashMapResource.put("lang.command.notifier.message.format", "Введите команду /notifier [hh:mm] [city]");
    }

}