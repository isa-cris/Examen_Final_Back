package com.primerparcial.primer.parcial.service;

import com.primerparcial.primer.parcial.dto.CarDTO;
import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.model.User;

import java.util.List;

public interface RestCarService {
    CarDTO getByid(Long id);
    List<CarDTO> getAllCars();
    CarDTO saveCar(Long id, Long user_id);
    Boolean addCarToUser(Long user_id, Car car);

}
