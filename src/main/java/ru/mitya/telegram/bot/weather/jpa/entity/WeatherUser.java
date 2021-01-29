package ru.mitya.telegram.bot.weather.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name = "user_weather_bot")
public class WeatherUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long weatherUserId;

    @Column(name = "chat")
    private Integer chatId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "is_bot")
    private Boolean isBot;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "name")
    private String userName;

    @Column(name = "language_code")
    private String languageCode;

    @OneToMany(mappedBy = "weatherUser", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionList;

    public WeatherUser(){

    }
    public WeatherUser(Integer chatId, String firstName, Boolean isBot, String lastName,
                String userName, String languageCode, List<Subscription> subscriptionList) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.isBot = isBot;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
        this.subscriptionList = subscriptionList;
    }

}


