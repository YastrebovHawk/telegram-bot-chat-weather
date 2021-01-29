package ru.mitya.telegram.bot.weather.command.notifier;

import java.time.LocalTime;

interface NotifierCommandParams {

    LocalTime getTime();
    String getCity();

}
