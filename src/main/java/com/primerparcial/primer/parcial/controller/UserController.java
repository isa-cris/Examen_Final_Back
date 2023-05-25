package com.primerparcial.primer.parcial.controller;

import com.primerparcial.primer.parcial.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.primerparcial.primer.parcial.model.User;
import com.primerparcial.primer.parcial.service.UserService;
import com.primerparcial.primer.parcial.service.UserServiceImp;
import com.primerparcial.primer.parcial.utils.ApiResponse;
import com.primerparcial.primer.parcial.utils.Constants;
import org.apache.coyote.Response;
import com.primerparcial.primer.parcial.service.CarService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;
    @Autowired
    private CarService carService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{user_id}")
    public ResponseEntity findUserById(@PathVariable Long user_id) {
        try {
            User user = userService.getUser(user_id);
            if (user != null) {
                List<Car> cars = carService.getCarsByUser(user);

                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("cars", cars);

                apiResponse = new ApiResponse(Constants.REGISTER_FOUND, response);
                return new ResponseEntity(apiResponse, HttpStatus.OK);
            } else {
                apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "")
    public ResponseEntity saveUser(@RequestBody User user) {
        Boolean userResp = userService.createUser(user);

        if (userResp == true) {
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
        return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "")
    public ResponseEntity alluser() {
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_LIST, userService.alluser());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{user_id}")
    public ResponseEntity updateUser(@PathVariable Long user_id, @RequestBody User user) {
        Boolean userDB = userService.updateUser(user_id, user);
        try {
            if (userDB == null) {
                apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");

                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }
            apiResponse = new ApiResponse(Constants.REGISTER_UPDATED, userService.getUser(user_id));
            return new ResponseEntity(apiResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "user/{user_id}")
    public ResponseEntity deleteVehiculo(@PathVariable Long user_id, User user){
        Map response = new HashMap();
        Boolean userDB = userService.deleteUser(user_id, user);
        try{
            if (userDB == null){
                apiResponse = new ApiResponse( Constants.REGISTER_NOT_FOUND,"");
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }else {
                apiResponse =new ApiResponse(Constants.DELETE_CAR, userService.deleteUser(user_id, user));
                return new ResponseEntity(response, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            apiResponse= new ApiResponse(Constants.REGISTER_BAD, user);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{user_id}/cars/count")
    public ResponseEntity getCarCountByUser(@PathVariable("user_id") Long userId) {
        try {
            int carCount = userService.getCarCountByUser(userId);
            apiResponse = new ApiResponse(Constants.CAR_COUNT_FOUND, carCount);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

}