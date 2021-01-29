package ru.mitya.telegram.bot.weather.command.delete_notifier;

import java.time.LocalTime;

interface DeleteNotifierCommandParams {

    LocalTime getTime();
    String getCity();
    
}
