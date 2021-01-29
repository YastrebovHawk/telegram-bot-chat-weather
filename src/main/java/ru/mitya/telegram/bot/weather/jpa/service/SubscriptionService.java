package ru.mitya.telegram.bot.weather.jpa.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.mitya.telegram.bot.weather.notice.BuilderUser;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;
import ru.mitya.telegram.bot.weather.jpa.entity.WeatherUser;
import ru.mitya.telegram.bot.weather.jpa.repo.NotifierTimeCityRepo;
import ru.mitya.telegram.bot.weather.jpa.repo.WeatherUserRepo;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriptionService implements ISubscriptionService {

    private final WeatherUserRepo weatherUserRepo;
    private final NotifierTimeCityRepo notifierTimeCityRepo;

    @Override
    public boolean save(User user, LocalTime time, String city) {
        log.info("Сохранение параметров уведомления: время - {}, город - {}, юзера {}",time, city, user.toString());
        WeatherUser weatherUser = weatherUserRepo.findByChatId(user.getId());
        if (weatherUser != null) {
            log.debug("Юзер {} существует в базе данных", user.getId());
            Subscription subscription = isNotifier(time, city, weatherUser);
            if (subscription == null) {
                log.debug("Уведомление не существует у юзера {}, с параметрами {}, {}", user.getId(), time, city);
                subscription = new Subscription(time, city, weatherUser);
                notifierTimeCityRepo.save(subscription);
                log.info("Уведомление юзера {} с параметрами: время - {}, город - {}, сохранено", user.getId(), time, city);
                return true;
            } else {
                log.info("Уведомление юзера {} с параметрами: время - {}, город - {}, существует, поэтому не сохранено", user.getId(), time, city);
                return false;
            }
        } else {
            log.debug("Юзера {} не существует в базе данных", user.getId());
            weatherUser = BuilderUser.createWeatherUser(user);
            Subscription subscription = new Subscription(time, city, weatherUser);
            weatherUser.getSubscriptionList().add(subscription);
            weatherUserRepo.save(weatherUser);
            log.info("Юзер {} сохранен в базе данных", user.getId());
            notifierTimeCityRepo.save(subscription);
            log.info("Юзер {} с параметрами уведомления: время - {}, город - {}, сохранены", user.getId(), time, city);
            return true;
        }
    }

    @Override
    public boolean delete(User user, LocalTime time, String city) throws NumberFormatException {
        log.info("Удаления уведомления с параметрами: время - {}, город - {}, юзера {}",time, city, user.getId());
        WeatherUser weatherUser = weatherUserRepo.findByChatId(user.getId());
        if (weatherUser != null) {
            log.debug("Юзер {} существует в базе данных", user.getId());
            Subscription subscription = isNotifier(time, city, weatherUser);
            if (subscription != null) {
                notifierTimeCityRepo.delete(subscription);
                log.info("Уведомление с параметрами: время - {}, город - {}, юзера {} удалено", time, city, user.getId());
                return true;
            } else {
                log.info("Уведомление с параметрами: время - {}, город - {}, юзера {} не существует", time, city, user.getId());
                return false;
            }
        } else {
            log.info("Юзера {} не существует в базе данных", user.getId());
            return false;
        }
    }

    @Override
    public List<Subscription> reviewNotifier(Integer userId) {
        log.debug("Поиск юзера {} в базе данных", userId);
        WeatherUser weatherUser = weatherUserRepo.findByChatId(userId);
        List<Subscription> list;
        if (weatherUser != null) {
            list = notifierTimeCityRepo.findByWeatherUser(weatherUser);
        } else {
            list = null;
        }
        return list;
    }

    public Subscription isNotifier(LocalTime time, String city, WeatherUser user) {
        log.debug("Проверка наличия уведомления  с параметрами {},  юзера {} в базе данных", time, user.getChatId());
        Subscription subscription = notifierTimeCityRepo.findByTimeAndCityAndWeatherUser(time, city, user);
        return subscription;
    }

    public boolean isNotifier(LocalTime time, String city, User user) {
        WeatherUser weatherUser = weatherUserRepo.findByChatId(user.getId());
        log.debug("Проверка наличия уведомления  с параметрами {},  юзера {} в базе данных", time, user.getId());
        Subscription subscription = notifierTimeCityRepo.findByTimeAndCityAndWeatherUser(time, city, weatherUser);
        return subscription == null;
    }

}
