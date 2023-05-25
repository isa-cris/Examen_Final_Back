package com.primerparcial.primer.parcial.service;

import com.primerparcial.primer.parcial.model.Car;
import com.primerparcial.primer.parcial.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import com.primerparcial.primer.parcial.model.User;
import com.primerparcial.primer.parcial.repository.UserRepository;
import com.primerparcial.primer.parcial.utils.JWTUtil;

import java.util.List;
import java.util.Optional;

@Lazy
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;
    @Autowired
    private JWTUtil jwtutil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(Long user_id) {
        return userRepository.findById(user_id).get();
    }

   @Override
   public Boolean createUser(User user) {
       try {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           userRepository.save(user);
           return true;
       } catch (Exception e) {
           return false;
       }
   }

    @Override
    public List<User> alluser() {
        return userRepository.findAll();
    }//lista de todos los usuarios

    @Override
    public Boolean updateUser(Long user_id, User user) {
        try{
            User userBD = userRepository.findById(user_id).get();
            userBD.setFirstName(user.getFirstName());
            userBD.setLastName(user.getLastName());
            userBD.setAddress(user.getAddress());
            userBD.setBirthday(user.getBirthday());
            userBD.setPassword(passwordEncoder.encode(user.getPassword()));
            User userUp = userRepository.save(userBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @PostMapping(value = "auth/login")
    public String login(User user) {
        Optional<User> userBd = userRepository.findByEmail(user.getEmail());
        if (userBd.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(user.getPassword(),userBd.get().getPassword())){
            throw new RuntimeException("La contraseña es incorecta");
        }
        return jwtutil.create(String.valueOf(userBd.get().getUser_id()),
                String.valueOf(userBd.get().getEmail()));
    }

    public Boolean deleteUser(Long user_id, User user) {
        try {
            User userDB = userRepository.findById(user_id).get();
            if (userDB == null){
                return false;
            }
            userRepository.delete(userDB);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean addCarToUser(Long user_id, Car car) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            car.setUser(user);
            carService.createCar(car);
            return true;
        }
        return false;
    }

    @Override
    public int getCarCountByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            List<Car> cars = carRepository.findByUser(user);
            return cars.size();
        }
        return 0;
    }

}
