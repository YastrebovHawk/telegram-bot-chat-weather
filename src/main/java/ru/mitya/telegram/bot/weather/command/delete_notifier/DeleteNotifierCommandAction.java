package ru.mitya.telegram.bot.weather.command.delete_notifier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class DeleteNotifierCommandAction implements DeleteNotifierCommandParams{

    private final LocalTime time;
    private final String city;
    private final DeleteNotifierCommandMode deleteNotifierCommandMode;

    public static DeleteNotifierCommandAction createDeleteSubscriptionForUser(LocalTime time, String city){
        return new DeleteNotifierCommandAction(time, city, DeleteNotifierCommandMode.DELETE_SUBSCRIPTION_FOR_USER);
    }

    public static DeleteNotifierCommandAction createIncorrectTimeFormatError(){
        return new DeleteNotifierCommandAction(null, null, DeleteNotifierCommandMode.INCORRECT_TIME_FORMAT_ERROR);
    }

    public static DeleteNotifierCommandAction createInputFormatError(){
        return new DeleteNotifierCommandAction(null, null, DeleteNotifierCommandMode.INPUT_FORMAT_ERROR);
    }

}
