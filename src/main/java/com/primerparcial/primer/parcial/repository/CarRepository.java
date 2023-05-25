package com.primerparcial.primer.parcial.repository;


import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
}
