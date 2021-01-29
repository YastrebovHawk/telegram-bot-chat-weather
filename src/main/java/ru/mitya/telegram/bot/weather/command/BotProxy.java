package ru.mitya.telegram.bot.weather.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author m.yastrebov
 */
@Slf4j
@AllArgsConstructor
public class BotProxy {

    private final AbsSender absSender;

    public void execute(SendMessage message) throws TelegramApiException {
        absSender.execute(message);
        log.info("Отправлено сообщение {}, юзеру {}",
                message.getText(),
                message.getChatId()
        );
    }

    public void execute(SendPhoto message) throws TelegramApiException {
        absSender.execute(message);
        log.info("Отправлено изображение юзеру {}", message.getChatId());
    }

    public static BotProxy create(AbsSender absSender) {
        return new BotProxy(absSender);
    }

}
