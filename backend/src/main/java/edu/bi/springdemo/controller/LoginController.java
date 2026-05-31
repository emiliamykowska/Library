package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.LoginDTO;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.service.LoginService;
import edu.bi.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//----NORMAL----

@RestController
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService){
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody LoginDTO loginDTO){
        String token = loginService.login(loginDTO.getUsername(), loginDTO.getPassword());

        User user = userService.findByUsername(loginDTO.getUsername());

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("role", user.getRole().name());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }



// ----SESSION----
//@RestController
//public class LoginController {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public LoginController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login (@RequestBody LoginDTO loginDTO, HttpSession session){
//        User user = userRepository.findByUsername(loginDTO.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found!"));
//
//        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
//            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
//        }
//
//        session.setAttribute("user", user); //so browser remembers the user in cookie
//        String role = "ROLE_" + user.getRole().name();
//
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(
//                        user.getUsername(),
//                        null,
//                        List.of(new SimpleGrantedAuthority(role)));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); //set manually so user is remembered
//
//        return new ResponseEntity<>("You were logged in!", HttpStatus.OK);
//    }
//
//}

    // example of using Gson class for creating object from JSON
    //Gson test = new Gson();
    //User user = test.fromJson(/*string from request*/, User.class);
}