package ru.mitya.telegram.bot.weather.command.notifier;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;
import ru.mitya.telegram.bot.weather.jpa.service.SubscriptionService;

import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author m.yastrebov
 */
@Service
@Slf4j
@AllArgsConstructor
class NotifierTelegramHelper {

    private final SubscriptionService subscriptionService;
    private final ResourceBundle resourceBundle;

    void sendListOfSubscriptions(BotProxy botProxy, User user) throws TelegramApiException {
        log.debug("Поиск подписок для пользователя {}", user.getId());
        StringBuilder sb = new StringBuilder();
        List<Subscription> list = subscriptionService.reviewNotifier(user.getId());
        if (list.size() != 0) {
            list.forEach((b) -> sb.append(b.toString()));
        } else {
            sb.append("Уведомлений нет");
        }
        botProxy.execute(new SendMessage(user.getId().toString(), sb.toString()));
    }

    void saveSubscription(BotProxy botProxy, User user, LocalTime time, String city) throws TelegramApiException {
        if (!subscriptionService.save(user, time, city)) {
            final SendMessage message = new SendMessage(user.getId().toString(),
                    resourceBundle.getString("lang.command.notifier.message.notifier"));
            botProxy.execute(message);
        }
    }

    void handleIncorrectTimeFormatError(BotProxy botProxy, User user) throws TelegramApiException {
        log.warn("Введен неправильный формат времени юзером {}", user.getId());
        botProxy.execute(new SendMessage(user.getId().toString(),
                resourceBundle.getString("lang.command.notifier.message.format.time")));
    }

    void handleMissingTimeError(BotProxy botProxy, User user) throws TelegramApiException {
        log.warn("Введен неправиьный формат аргументов юзера {}", user.getId());
        botProxy.execute(new SendMessage(user.getId().toString(),
                resourceBundle.getString("lang.command.notifier.message.format")));
    }

    void handleInputFormatError(BotProxy botProxy, User user) throws TelegramApiException{
        log.error("Произошла ошибка {} при отправке юзеру {} сообщения", NullPointerException.class, user);
        botProxy.execute(new SendMessage(user.getId().toString(), resourceBundle.getString("lang.command.notifier.message.nullformat")));
    }

}
