package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoginDTO;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.repository.UserRepository;
import edu.bi.springdemo.security.JWTTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder, JWTTokenService jwtTokenService, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        //simulating getting user from database by username
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if(!matches){
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
        String role = "ROLE_" + user.getRole().name();
        String token = jwtTokenService.generateToken(user.getUsername(), role);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }
}