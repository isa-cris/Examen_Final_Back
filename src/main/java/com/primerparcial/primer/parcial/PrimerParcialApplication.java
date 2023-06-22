package com.primerparcial.primer.parcial;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@Data
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class PrimerParcialApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimerParcialApplication.class, args);
	}

}
