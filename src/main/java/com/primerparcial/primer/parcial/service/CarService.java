package com.primerparcial.primer.parcial.service;

import com.primerparcial.primer.parcial.model.Car;

import java.util.List;

public interface CarService {

    Boolean createCar(Car car);
    Car getCar(Long id);
    List<Car> getAllCars();
    Boolean updateCar(Car car, Long id);
    Boolean deleteCar(Long id, Car car);

}
