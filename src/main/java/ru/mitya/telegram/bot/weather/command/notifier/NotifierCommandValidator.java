package ru.mitya.telegram.bot.weather.command.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@Component
@Slf4j
class NotifierCommandValidator {

    public  NotifierCommandAction convertArguments(String[] arguments) {
        if (arguments == null){
            return NotifierCommandAction.createListOfAllSubscriptionsAction();
        }
        final int argumentLength = arguments.length;
        if (argumentLength == 0) {
            return NotifierCommandAction.createListOfAllSubscriptionsAction();
        } else if (argumentLength == 1) {
            return NotifierCommandAction.createTimeMissingError();
        } else {
            try {
                LocalTime time = LocalTime.parse(arguments[0]);
                StringBuilder sb = new StringBuilder();
                Arrays.stream(arguments).skip(1).forEach((b) -> sb.append(b).append(" "));
                sb.delete(sb.length() - 1, sb.length());
                return NotifierCommandAction.createSaveSubscriptionAction(time, sb.toString());
            } catch (DateTimeParseException e) {
                return NotifierCommandAction.createIncorrectTimeFormatError();
            }
        }
    }

}