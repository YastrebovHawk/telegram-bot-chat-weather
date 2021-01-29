package ru.mitya.telegram.bot.weather.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mitya.telegram.bot.weather.client.WeatherClient;
import ru.mitya.telegram.bot.weather.client.feign.FeignClient;

@Configuration
public class ClientConfiguration {

    @Value("${api.openweathermap.baseUrl}")
    String baseUrl;

    @Bean
    Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    WeatherClient feignClient(){
        return  Feign.builder()
                .decoder(new GsonDecoder())
                .target(FeignClient.class, baseUrl);
    }

}
