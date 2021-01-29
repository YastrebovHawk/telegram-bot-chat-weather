package ru.mitya.telegram.bot.weather.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotCommands {

    START("start"),
    HELP("help"),
    NOTIFIER("notifier"),
    WEATHER("weather"),
    DELETE("delete");

    private final String identifier;

}
