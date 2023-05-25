package com.primerparcial.primer.parcial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference //controla la serializacion
    private User user;

    private String car;
    private String car_model;
    private String car_color;
    private String car_model_year;

    @Column(unique = true) //valores unicos
    private String car_vin;

    private String price;
    private String availability;
}
