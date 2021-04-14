package com.henry.news.services;


import com.google.gson.Gson;
import com.henry.news.response.Main;
import com.henry.news.response.OpenWeatherResponse;
import com.henry.news.response.WeatherResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class ApiCallService {
// creo cliente
    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @CircuitBreaker(name = "WeatherAPI", fallbackMethod = "fallback")
    public WeatherResponse callAPI() throws IOException,InterruptedException{
// creo llamado
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=Santiago"))
                .header("x-rapidapi-key", "d74ca6e16amsh1b4dc511e9e8842p1043cdjsna5be76153d6f")
                .header("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        //envio llamado
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        //manejo respuesta
        final WeatherResponse weatherResponse = new Gson().fromJson(response.body(), WeatherResponse.class);
        System.out.println(weatherResponse);

        if (RandomUtils.nextBoolean()) {
            throw new IOException("Hacer un random y que falle para probar el circuit breaker");
        }

        return weatherResponse;
    }
    private WeatherResponse fallback(final Throwable t) throws IOException, InterruptedException{
        log.error(t.getStackTrace().toString()); //todo el error
        return WeatherResponse.builder().build();
    }

    @CircuitBreaker(name = "ApiWeather2",fallbackMethod = "fallback2")
    public OpenWeatherResponse callAPI2() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q=santiago&appid=c1b3f5afa9a7ff7d07eba337845a8b06"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println( response.body());

        final OpenWeatherResponse openWeatherResponse = new Gson().fromJson(response.body(), OpenWeatherResponse.class);

        if(RandomUtils.nextBoolean()){
            throw new IOException("Probando de nuevo...");
        }

        System.out.println(openWeatherResponse);

        return openWeatherResponse;
    }

    private OpenWeatherResponse fallback2(final Throwable t){
        log.error(t.getStackTrace().toString()); //todo el error
        Main main = new Main(0d,0d,0d,0d,0,0);
        return OpenWeatherResponse
                .builder()
                .main(main)
                .name("Ups, parece que se nos cayeron todas las antenas...:(")
                .build();
    }



}
