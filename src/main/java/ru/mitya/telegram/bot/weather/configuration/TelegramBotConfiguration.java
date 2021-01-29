package ru.mitya.telegram.bot.weather.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import ru.mitya.telegram.bot.weather.command.UTF8Control;

import java.util.ResourceBundle;

@Configuration
public class TelegramBotConfiguration {

    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private int proxyPort;

    @Bean
    DefaultBotOptions defaultBotOptions(){
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);
        return options;
    }

    @Bean
    TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }

    @Bean
    HelpCommand helpCommand(){
        return new HelpCommand();
    }

    @Bean
    ResourceBundle resourceBundle(){
        return ResourceBundle.getBundle("messages", new UTF8Control());
    }

}
