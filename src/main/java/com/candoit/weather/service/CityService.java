package com.candoit.weather.service;


import com.candoit.weather.api.ApiCallService;
import com.candoit.weather.model.City;

import com.candoit.weather.repository.CityRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private final ApiCallService apiCallService;

    public CityService(ApiCallService apiCallService) {
        this.apiCallService = apiCallService;
    }

    /**
     * This function requests to update the database based on certain parameters:
     * -> if the city already exists, it only updates the temperature field, otherwise, calls to create a new registry
     *
     * @param lecturas cities read from the api
     */
    @Operation(description = "update temperatures", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))) })
    public void update(List<City> lecturas) {
        City c=null;
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd L yy H:mm:ss");
        date = LocalDateTime.parse(date.format(formatter), formatter);
        for (City city : lecturas
             ) {
            c= cityRepository.findCityById(city.getId());
            if (c == null) {
                city.setLastUpdate(date);
                cityRepository.save(city);
            }
            else {
                c.setTemperature(city.getTemperature());
                c.setLastUpdate(date);
                cityRepository.save(c);
            }
        }

    }
    /**
     * This function calls the getWeather() method from apiCallService every 5 minutes and then updates every city temperature
     * calling the respective repository
     *
     *
     */
    @Scheduled(fixedDelay = 300000L)
    public void obtenerlecturas() throws IOException, InterruptedException {
        List<City> lecturas =  apiCallService.getWeather();
        update(lecturas);
    }


    /**
     * This function calls the repository to bring a page of cities from the database:
     * @Param page page number
     * @param size size of page
     * @return a pageable of cities
     */
    @CircuitBreaker(name = "get-list")
    @Operation(description = "List all cities", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Page.class))), responseCode = "200") })
    public Page<City> getWeatherfromAllCities(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
       return cityRepository.findAll(pageable);
    }
}
