package com.primerparcial.primer.parcial.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primerparcial.primer.parcial.dto.CarDTO;
import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class
RestCarServiceImp {
    private final RestTemplate restTemplate;
    private final CarRepository carRepository;

    public Object getById(Long id) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CarDTO> entity = new HttpEntity<CarDTO>(headers);
        String response = restTemplate.exchange("https://myfakeapi.com/api/cars/" + id.toString(), HttpMethod.GET, entity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO carDTO = objectMapper.readValue(response.substring(7), CarDTO.class);

        return carDTO;
    }


    public Object getAllCars() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CarDTO> entity = new HttpEntity<CarDTO>(headers);

        return restTemplate.exchange("https://myfakeapi.com/api/cars/" , HttpMethod.GET, entity, Object.class).getBody();

    }

    public Object saveCar(Long id) throws JsonProcessingException{

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CarDTO> entity = new HttpEntity<CarDTO>(headers);
        String response = restTemplate.exchange("https://myfakeapi.com/api/cars/" + id.toString(), HttpMethod.GET, entity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        Car carDTO = objectMapper.readValue(response.substring(7), Car.class);

        List<Car> carro = carRepository.findAll();
        boolean p=true;
        for (Car car:carro) {
            if(car.getCar_vin().equals(carDTO.getCar_vin())){
                p=false;
            }
        }
        if(p==true) {
            try {
                Car carSave = carRepository.save(carDTO);
                return carDTO;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
