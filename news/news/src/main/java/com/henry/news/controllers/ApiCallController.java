package com.henry.news.controllers;


import com.henry.news.response.WeatherResponse;
import com.henry.news.services.ApiCallService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/weather")
@RestController
public class ApiCallController {

    @Autowired
    ApiCallService apiCallService;

    @GetMapping
    @Operation(summary = "Weather app")
    public Object callAPI() {
        try {
            WeatherResponse weatherResponse = apiCallService.callAPI();
            if (weatherResponse.getLocation() == null){
                return apiCallService.callAPI2();
            }
            return weatherResponse;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
