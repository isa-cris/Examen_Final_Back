package com.primerparcial.primer.parcial.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.primerparcial.primer.parcial.dto.CarDTO;
import com.primerparcial.primer.parcial.service.RestCarServiceImp;
import com.primerparcial.primer.parcial.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/cars/")
public class RestCarController {

    private final RestCarServiceImp restCarServiceImp;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping(value = "/{id}")
    public ResponseEntity getCar(@PathVariable Long id) throws JsonProcessingException {
        return new ResponseEntity(restCarServiceImp.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CarDTO>> getAllCars() throws JsonProcessingException {
        return new ResponseEntity(restCarServiceImp.getAllCars(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity saveCar(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        if (!validateToken(token)) {
            return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(restCarServiceImp.saveCar(id), HttpStatus.ACCEPTED);
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
