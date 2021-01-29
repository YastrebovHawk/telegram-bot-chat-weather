package ru.mitya.telegram.bot.weather.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

@Slf4j
@Component
public  class WeatherBot extends TelegramLongPollingCommandBot {

    private static final String BOT_USERNAME = "здесь должно быть названия бота";
    private static final  String BOT_TOKEN = "здесь должен быть токен";

    private  List<BotCommand> list;


    public WeatherBot(@Autowired DefaultBotOptions defaultBotOptions, List<BotCommand> list,
                      @Autowired TelegramBotsApi telegramBotsApi) {
        super(defaultBotOptions);
        this.list = list;
        for (BotCommand botCommand : list) {
            register(botCommand);
            log.info("Регистрация команды {}",botCommand.getCommandIdentifier());
        }
        try {
            telegramBotsApi.registerBot(this);
            log.info("Регистрация бота");
        } catch (TelegramApiRequestException e) {
            log.error("Произошла ошибка регистрации бота");
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
          }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
