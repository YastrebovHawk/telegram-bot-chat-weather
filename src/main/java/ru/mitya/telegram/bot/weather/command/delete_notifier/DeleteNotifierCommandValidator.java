package ru.mitya.telegram.bot.weather.command.delete_notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
class DeleteNotifierCommandValidator {

    public DeleteNotifierCommandAction convertArguments(String[] arguments) {
        if (arguments == null){
            return DeleteNotifierCommandAction.createInputFormatError();
        }
        final int argumentLength = arguments.length;
        if (argumentLength <= 1) {
            return DeleteNotifierCommandAction.createInputFormatError();
        } else {
            try {
                LocalTime time = LocalTime.parse(arguments[0]);
                final String collect = Arrays.stream(arguments).skip(1)
                        .collect(Collectors.joining(" "));
                return DeleteNotifierCommandAction.createDeleteSubscriptionForUser(time, collect);
            } catch (DateTimeParseException e) {
                return DeleteNotifierCommandAction.createIncorrectTimeFormatError();
            }
        }
    }

}
