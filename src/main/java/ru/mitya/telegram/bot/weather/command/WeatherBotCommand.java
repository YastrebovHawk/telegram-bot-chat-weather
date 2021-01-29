package ru.mitya.telegram.bot.weather.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class WeatherBotCommand extends BotCommand {

    public WeatherBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
            super.processMessage(absSender, message, arguments);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    }

    protected void setMessageText(AbsSender absSender, User user, String text) throws TelegramApiException {
        absSender.execute(new SendMessage(user.getId().toString(), text));
        log.info("Отправлено сообщение {}, юзеру {}", text, user.getId());
    }

    protected void setMessagePhoto(AbsSender absSender, User user, SendPhoto sendPhoto) throws TelegramApiException{
        absSender.execute(sendPhoto.setChatId(user.getId().toString()));
        log.info("Отправлено изображение юзеру {}", user.getId());
    }

}
