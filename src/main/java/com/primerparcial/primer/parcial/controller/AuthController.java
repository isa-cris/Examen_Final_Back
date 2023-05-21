package com.primerparcial.primer.parcial.controller;

import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.service.CarService;
import com.primerparcial.primer.parcial.utils.ApiResponse;
import com.primerparcial.primer.parcial.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CarService carService;
    private ApiResponse apiResponse;
    Map data= new HashMap<>();

    @PostMapping(value = "/login")
    public ResponseEntity login (@RequestBody Car car) {

        try {
            data.put("token", carService.login(car));
            apiResponse = new ApiResponse(Constants.CAR_LOGIN, data);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(e.getMessage(), "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }
}
