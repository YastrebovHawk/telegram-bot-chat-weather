package ru.mitya.telegram.bot.weather;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableFeignClients
public class TelegramBotWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotWeatherApplication.class, args);
    }

    @PostConstruct
    void initializer() {
        ApiContextInitializer.init();
    }

}
