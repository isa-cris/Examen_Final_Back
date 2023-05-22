package com.primerparcial.primer.parcial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primerparcial.primer.parcial.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);
}
