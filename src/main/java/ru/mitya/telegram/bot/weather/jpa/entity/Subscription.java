package ru.mitya.telegram.bot.weather.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Setter(value = AccessLevel.PRIVATE)
@Getter
@Table(name = "notifier_time_city")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer subscriptionId ;

    @Column(name = "timer")
    private LocalTime time;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "weather_user")
    private WeatherUser weatherUser;

    public Subscription() {
    }

    public Subscription(LocalTime time, String city, WeatherUser weatherUser) {
        this.time = time;
        this.city = city;
        this.weatherUser = weatherUser;
    }

    public String toString() {
        return "Уведомления: \n" + city + " " + time + "\n";
    }

}
