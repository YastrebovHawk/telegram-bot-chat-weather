package ru.mitya.telegram.bot.weather.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mitya.telegram.bot.weather.jpa.entity.WeatherUser;


public interface WeatherUserRepo extends JpaRepository<WeatherUser, Long> {

    WeatherUser findByChatId(Integer chatId);

}
