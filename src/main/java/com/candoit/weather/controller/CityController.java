package com.candoit.weather.controller;

import com.candoit.weather.model.City;
import com.candoit.weather.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping(value="/temperatures")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService)
    {
        this.cityService = cityService;

    }

    /**
     * This function gets the temperatures from all the cities.
     *
     * @param page number
     * @param size of page
     *
     * @return ModelAndView Object
     */
    @GetMapping("")
    @Tag(name = "getAllTemperatures")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getAllTemperatures(@RequestParam(value="page",defaultValue="0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        ModelAndView mav = new ModelAndView("list-cities");
        Page<City> cityPage;
        cityPage = cityService.getWeatherfromAllCities(page,size);

        mav.addObject("cityPage", cityPage);
        int totalPages = cityPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mav.addObject("pageNumbers", pageNumbers);
        }
        return mav;
    }




}