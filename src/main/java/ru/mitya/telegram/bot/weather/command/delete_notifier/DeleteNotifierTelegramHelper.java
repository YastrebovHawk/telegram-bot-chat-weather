package ru.mitya.telegram.bot.weather.command.delete_notifier;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mitya.telegram.bot.weather.command.BotProxy;
import ru.mitya.telegram.bot.weather.jpa.service.SubscriptionService;

import java.time.LocalTime;
import java.util.ResourceBundle;

@Service
@Slf4j
@AllArgsConstructor
class DeleteNotifierTelegramHelper {

    private final SubscriptionService subscriptionService;
    private final ResourceBundle resourceBundle;

    void deleteSubscriptionForUser(BotProxy botProxy, User user, LocalTime time, String city) throws TelegramApiException {
        log.debug("Пришло 2 аргумента {}, {} от юзера {}", time, city, user.getId());
        if (subscriptionService.delete(user, time, city)) {
            log.debug("Уведомление юзера {} с параметрами {}, {} удалено", user.getId(), time, city);
            botProxy.execute(new SendMessage( user.getId().toString(), resourceBundle.getString("lang.command.delete.notifier.message.delete")));
        }
        else {
            log.debug("Уведомление юзера {} с параметрами {}, {} не существует", user.getId(), time, city);
            botProxy.execute(new SendMessage(user.getId().toString(), resourceBundle.getString("lang.command.delete.notifier.message.notnotifier")));
        }
    }

    void handleIncorrectTimeFormatError(BotProxy botProxy, User user) throws TelegramApiException {
        log.warn("Введен неправильный формат времени юзером {}", user.getId());
        botProxy.execute(new SendMessage(user.getId().toString(),
                resourceBundle.getString("lang.command.notifier.message.format.time")));
    }

    void handleInputFormatError(BotProxy botProxy, User user) throws  TelegramApiException {
        log.warn("Введен неправиьный формат аргументов юзера {}", user.getId());
        botProxy.execute(new SendMessage( user.getId().toString(), resourceBundle.getString("lang.command.delete.notifier.message.format")));
    }

}
