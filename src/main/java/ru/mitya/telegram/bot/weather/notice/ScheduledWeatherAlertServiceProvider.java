package ru.mitya.telegram.bot.weather.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ScheduledWeatherAlertServiceProvider {

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
    private final UserSubscriptionRunnable userSubscriptionRunnable;

    @Autowired
    public ScheduledWeatherAlertServiceProvider(UserSubscriptionRunnable userSubscriptionRunnable) {
        this.userSubscriptionRunnable = userSubscriptionRunnable;
    }

    @PostConstruct
    public void scheduleSubscriptions(){
        log.info("Запуск сервиса потоков по оповещению");
        service.scheduleAtFixedRate(userSubscriptionRunnable, 0, 1, TimeUnit.MINUTES);
    }

}


