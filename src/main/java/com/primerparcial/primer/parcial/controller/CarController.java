package com.primerparcial.primer.parcial.controller;

import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.model.User;
import com.primerparcial.primer.parcial.service.CarService;
import com.primerparcial.primer.parcial.service.CarServiceImp;
import com.primerparcial.primer.parcial.service.UserService;
import com.primerparcial.primer.parcial.utils.ApiResponse;
import com.primerparcial.primer.parcial.utils.Constants;
import com.primerparcial.primer.parcial.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@CrossOrigin
public class CarController {

    @Autowired
    private final CarServiceImp carServiceImp;
    @Autowired
    private CarService carService;
    private ApiResponse apiResponse;

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;


    @PostMapping(value = "")
    public ResponseEntity saveCar(@RequestBody Car car,@RequestHeader(value = "Authorization") String token) {
        Map response = new HashMap();
        Boolean carResp = carServiceImp.createCar(car);
        Car existingCar = carServiceImp.getCar(car.getId());
        if (!validateToken(token)) {
            return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
        }
        if (existingCar != null) {
            apiResponse = new ApiResponse(Constants.REGISTER_BAD, "El Id ya existe");
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }

        if (carResp) {
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED, "");
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD, "");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCar(@PathVariable Long id,@RequestHeader(value = "Authorization") String token ) {
        Car car = carService.getCar(id);
        if (!validateToken(token)) {
            return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
        }
        if (car != null) {
            User user = car.getUser();//ver las cosas del usuario
            Map<String, Object> response = new HashMap<>();//devuelve informacion
            response.put("car", car);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } else {
            ApiResponse apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List> getAllCars(@RequestHeader(value = "Authorization") String token) {

        System.out.println(token);
        try {
            if (!validateToken(token)) {
                return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
            }
                apiResponse = new ApiResponse(Constants.REGISTER_LIST, carServiceImp.getAllCars());
                return new ResponseEntity(apiResponse, HttpStatus.OK);
            } catch (Exception e) {
                apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
                return new ResponseEntity(apiResponse, HttpStatus.MULTI_STATUS);
            }
        }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCar(@RequestBody Car car, @PathVariable Long id) {
        Boolean carDB = carService.updateCar(car, id);
        try {
            if (carDB == null) {
                apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");

                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }
            apiResponse = new ApiResponse(Constants.REGISTER_UPDATED, carService.getCar(id));
            return new ResponseEntity(apiResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_BAD, car);
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteVehiculo(@PathVariable Long id, Car car,@RequestHeader(value = "Authorization") String token){
        Map response = new HashMap();
        Boolean carDB = carServiceImp.deleteCar(id, car);
        try{
            if (!validateToken(token)) {
                return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
            }
            if (carDB == null){
                apiResponse = new ApiResponse( Constants.REGISTER_NOT_FOUND,"");
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }else {
                apiResponse =new ApiResponse(Constants.DELETE_CAR, carService.deleteCar(id, car));
                return new ResponseEntity(response, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            apiResponse= new ApiResponse(Constants.REGISTER_BAD, car);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "user/{user_id}")//registrar un carro a un usuario
    public ResponseEntity registerCarForUser(@PathVariable Long user_id, @RequestBody Car car) {
        Boolean carRegistered = userService.addCarToUser(user_id, car);
        if (carRegistered) {
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.USER_NOT_FOUND, "");
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    private Boolean validateToken(String token){
        try{
            if(jwtUtil.getKey(token) != null){
                return true;
            }
            return  false;
        }catch (Exception e){
            return  false;
        }
    }
}
