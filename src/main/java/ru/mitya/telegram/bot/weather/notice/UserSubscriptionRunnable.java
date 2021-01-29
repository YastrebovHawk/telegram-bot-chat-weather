package ru.mitya.telegram.bot.weather.notice;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.mitya.telegram.bot.weather.command.weather.WeatherCommand;
import ru.mitya.telegram.bot.weather.jpa.entity.Subscription;
import ru.mitya.telegram.bot.weather.jpa.repo.NotifierTimeCityRepo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class UserSubscriptionRunnable implements Runnable{

    @Setter
    private DateTimeFormatter dateTimeFormatter;
    private final NotifierTimeCityRepo notifierTimeCityRepo;
    private final WeatherCommand weatherCommand;
    private final AbsSender absSender;

    @Autowired
    public UserSubscriptionRunnable(NotifierTimeCityRepo notifierTimeCityRepo,
                                    WeatherCommand weatherCommand,
                                    AbsSender absSender) {

        this.notifierTimeCityRepo = notifierTimeCityRepo;
        this.weatherCommand = weatherCommand;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.absSender = absSender;
    }

    @Override
    public void run() {
        log.info("Запуск потока {}", Thread.currentThread());
        LocalTime newTime = LocalTime.parse(dateTimeFormatter.format(LocalTime.now()));
        log.info("Поиск списка оповещений по времени {}, поток - {}", newTime, Thread.currentThread());
        List<Subscription> subscriptionList = notifierTimeCityRepo.findByTime(newTime);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(()->subscriptionList.forEach((b)->weatherCommand.execute(absSender,
                BuilderUser.createUser(b.getWeatherUser()),
                new Chat(), new String[]{b.getCity()})));
    }

}
