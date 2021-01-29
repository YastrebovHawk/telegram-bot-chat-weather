package ru.mitya.telegram.bot.weather.jpa.service;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;

import java.time.LocalTime;
import java.util.List;

public interface ISubscriptionService {

    boolean save(User user, LocalTime time, String city);
    boolean delete(User user, LocalTime time, String city);
    List<Subscription> reviewNotifier(Integer userId);

}
