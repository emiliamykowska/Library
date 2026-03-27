package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoginDTO;
import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.entity.User;
//import edu.bi.springdemo.exception.LoginPasswordException;
import edu.bi.springdemo.repository.UserRepository;
import edu.bi.springdemo.security.JWTTokenService;
//import edu.bi.springdemo.service.LoginService;
import edu.bi.springdemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//----NORMAL----

//@RestController
//public class LoginController {
//    private final PasswordEncoder passwordEncoder;
//    private final JWTTokenService jwtTokenService;
//    private final UserRepository userRepository;
////    private final LoginService loginService;
//
//    @Autowired
//    public LoginController(PasswordEncoder passwordEncoder, JWTTokenService jwtTokenService, UserRepository userRepository){
//        this.passwordEncoder = passwordEncoder;
//        this.jwtTokenService = jwtTokenService;
//        this.userRepository = userRepository;
////        this.loginService = loginService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
//        //simulating getting user from database by username
//        User user = userRepository.findByUsername(loginDTO.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//
//        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
//
//        if(!matches){
//            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
//        }
//        String role = "ROLE_" + user.getRole().name();
//        String token = jwtTokenService.generateToken(user.getUsername(), role);
//        return new ResponseEntity<>(token, HttpStatus.OK);
//
//    }



// ----SESSION----
@RestController
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginDTO loginDTO, HttpSession session){
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        session.setAttribute("user", user); //so browser remembers the user in cookie
        String role = "ROLE_" + user.getRole().name();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        List.of(new SimpleGrantedAuthority(role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); //set manually so user is remembered

        return new ResponseEntity<>("You were logged in!", HttpStatus.OK);
    }

}



// ---ERRORS----
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
//        String token = loginService.login(loginDTO.getUsername(), loginDTO.getPassword());
//
//        Map<String, String> map = new HashMap<>();
//        map.put("token", token);
//
//        return new ResponseEntity<>(new Gson().toJson(map), HttpStatus.OK);
//    }
//
//
//    //this works only for this controller
//    @ExceptionHandler(LoginPasswordException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public String resolveLoginPasswordException(LoginPasswordException e) {
//        Map<String, String> map = new HashMap<>();
//        map.put("message", e.getMessage());
//        map.put("timestamp", new Date().toString());
//        return new Gson().toJson(map);
//    }
//
//    // example of using Gson class for creating object from JSON
//    //Gson test = new Gson();
//    //User user = test.fromJson(/*string from request*/, User.class);
//}