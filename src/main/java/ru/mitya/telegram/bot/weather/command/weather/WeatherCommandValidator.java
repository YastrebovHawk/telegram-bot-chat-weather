package ru.mitya.telegram.bot.weather.command.weather;

import java.util.Arrays;

class WeatherCommandValidator {

    public static WeatherCommandAction convertArguments(String[] arguments) {
        final int argumentLength = arguments.length;
        if (argumentLength == 0) {
            return WeatherCommandAction.createInputFormatError();
        } else {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(arguments).forEach((b) -> sb.append(b).append(" "));
            sb.delete(sb.length() - 1, sb.length());
            return WeatherCommandAction.createWeatherInTheCity(sb.toString());
        }
    }

}
