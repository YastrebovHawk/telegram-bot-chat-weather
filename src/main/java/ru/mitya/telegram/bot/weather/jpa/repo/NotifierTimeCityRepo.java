package ru.mitya.telegram.bot.weather.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;
import ru.mitya.telegram.bot.weather.jpa.entity.WeatherUser;

import java.time.LocalTime;
import java.util.List;


public interface NotifierTimeCityRepo extends JpaRepository<Subscription, Long> {

    Subscription findByTimeAndCityAndWeatherUser(LocalTime time, String city, WeatherUser user);
    List<Subscription> findByWeatherUser(WeatherUser weatherUser);
    List<Subscription> findByTime(LocalTime time);

}
