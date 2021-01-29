package ru.mitya.telegram.bot.weather.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mitya.telegram.bot.weather.client.model.WeatherModel;
import ru.mitya.telegram.bot.weather.configuration.ClientConfiguration;

@ContextConfiguration(classes = ClientConfiguration.class)
@ExtendWith(SpringExtension.class)
public abstract class WeatherClientSpec {

    @Test
    void shouldFetWeatherModelByCity() throws Exception {
        final WeatherModel actualWeatherModel = getUUT().getWeatherModels("london");
        Assertions.assertEquals(actualWeatherModel.getName(), "london");
        Assertions.assertEquals(actualWeatherModel.getMain().getTemp(), 1.43);
        Assertions.assertEquals(actualWeatherModel.getMain().getHumidity(), 93);
        Assertions.assertEquals(actualWeatherModel.getWind().getSpeed(), 3.6);
        Assertions.assertEquals(actualWeatherModel.getWeather().get(0).getMain(), "Rain");
        Assertions.assertEquals(actualWeatherModel.getWeather().get(0).getIcon(), "10d");
    }

    abstract public WeatherClient getUUT();

}