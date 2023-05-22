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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;

    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable Long id) {
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
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

    @PutMapping(value = "/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user) {
        Boolean userDB = userService.updateUser(id, user);
        try {
            if (userDB == null) {
                apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");

                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }
            apiResponse = new ApiResponse(Constants.REGISTER_UPDATED, userService.getUser(id));
            return new ResponseEntity(apiResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "user/{id}")
    public ResponseEntity deleteVehiculo(@PathVariable Long id, User user){
        Map response = new HashMap();
        Boolean userDB = userService.deleteUser(id, user);
        try{
            if (userDB == null){
                apiResponse = new ApiResponse( Constants.REGISTER_NOT_FOUND,"");
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }else {
                apiResponse =new ApiResponse(Constants.DELETE_CAR, userService.deleteUser(id, user));
                return new ResponseEntity(response, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            apiResponse= new ApiResponse(Constants.REGISTER_BAD, user);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}