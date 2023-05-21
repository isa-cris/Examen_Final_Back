package com.primerparcial.primer.parcial.controller;

import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.service.CarService;
import com.primerparcial.primer.parcial.service.CarServiceImp;
import com.primerparcial.primer.parcial.utils.ApiResponse;
import com.primerparcial.primer.parcial.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CarController {

    @Autowired
    private final CarServiceImp carServiceImp;
    private CarService carService;
    private ApiResponse apiResponse;


    @PostMapping(value = "/car")
    public ResponseEntity saveCar(@RequestBody Car car){
        Map response = new HashMap();
        Boolean carResp = carServiceImp.createCar(car);

        if (carResp == true) {
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED, "");
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD,"");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/car/{id}")
    public ResponseEntity getCar(@PathVariable Long id){
        Map response = new HashMap();
        try{
            return new ResponseEntity(carServiceImp.getCar(id), HttpStatus.OK);
        }catch (Exception e){
            response.put("status","404");
            response.put("message","nese encontro el vehiculo");
            return new ResponseEntity(response, HttpStatus.MULTI_STATUS);
        }
    }

    @GetMapping(value = "/cars")
    public ResponseEntity<List> getAllCars(){
        Map response = new HashMap();
        try{
            apiResponse =new ApiResponse(Constants.REGISTER_LIST,carServiceImp.getAllCars() );
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse( Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity(response, HttpStatus.MULTI_STATUS);
        }
    }

    @PutMapping(value = "/car/{id}")
    public  ResponseEntity updateUser(@PathVariable Long id, @RequestBody Car car) {
        Map response = new HashMap();
        Boolean carDB = carServiceImp.updateCar(car, id);
        try {
            if (carDB == null) {
                apiResponse = new ApiResponse( Constants.REGISTER_NOT_FOUND,"");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
            apiResponse =new ApiResponse(Constants.REGISTER_UPDATED, carService.getCar(id));
            return new ResponseEntity(apiResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            apiResponse= new ApiResponse(Constants.REGISTER_BAD, car);
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "car/{id}")
    public ResponseEntity deleteVehiculo(@PathVariable Long id, Car car){
        Map response = new HashMap();
        Boolean carDB = carServiceImp.deleteCar(id, car);
        try{
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

}
