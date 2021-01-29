package ru.mitya.telegram.bot.weather.command.notifier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class NotifierCommandAction implements NotifierCommandParams {

    private final LocalTime time;
    private final String city;
    private final NotifierCommandMode notifierCommandMode;

    public static NotifierCommandAction createListOfAllSubscriptionsAction() {
        return new NotifierCommandAction(
                null, null,
                NotifierCommandMode.ALL_SUBSCRIPTIONS_FOR_USER
        );
    }

    public static NotifierCommandAction createIncorrectTimeFormatError() {
        return new NotifierCommandAction(
                null, null,
                NotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR
        );
    }

    public static NotifierCommandAction createTimeMissingError() {
        return new NotifierCommandAction(
                null, null,
                NotifierCommandMode.TIME_MISSING_ERROR
        );
    }

    public static NotifierCommandAction createSaveSubscriptionAction(LocalTime time, String city) {
        return new NotifierCommandAction(
                time, city,
                NotifierCommandMode.SAVE_SUBSCRIPTION
        );
    }

    public static NotifierCommandAction createInputFormatError(){
        return new NotifierCommandAction(
                null, null,
                NotifierCommandMode.INPUT_FORMAT_ERROR
        );
    }

}