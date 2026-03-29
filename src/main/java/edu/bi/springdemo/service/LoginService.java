package edu.bi.springdemo.service;

import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.LoginPasswordException;
import edu.bi.springdemo.repository.UserRepository;
import edu.bi.springdemo.security.JWTTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTTokenService jwtTokenService;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        JWTTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }


    public String login(String username, String password){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> LoginPasswordException.create("Incorrect login or password"));

        if(passwordEncoder.matches(password, user.getPassword())){
            return jwtTokenService.generateToken(username, "ROLE_" + user.getRole().name());
        } else {
            throw LoginPasswordException.create("Incorrect login or password");
        }

    }
}
