package com.candoit.weather.api;

import com.candoit.weather.model.City;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApiCallService {

    /**
     * This function gets the weather of all the cities from the remote api.
     * if theres a third party error coming from the requested url, it will make a fallback
     * @return List<City> Object
     */
    @CircuitBreaker(name= "weather", fallbackMethod = "fallback")
    public List<City> getWeather() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ws.smn.gob.ar/map_items/weather"))
                .method("GET",HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Type weatherListType = new TypeToken<ArrayList<City>>(){}.getType();

        Gson gson =  new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        ArrayList<City> weathers =gson.fromJson(response.body(), weatherListType);
       return weathers;

        }

   public City fallback(final Throwable t) {
       log.info("Fallback from api call: cause, {}" , t.toString());
        return City.builder().build();
   }


}

