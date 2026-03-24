package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.mapper.UserMapper;
import edu.bi.springdemo.service.UserService;
import edu.bi.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody UserDTO addUser(@RequestBody UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        User savedUser = userService.save(user);
        return userMapper.toDTO(savedUser);
    }

    @GetMapping
    public @ResponseBody Iterable<UserDTO> getAllReviews(){
        List<UserDTO> result = new ArrayList<>();

        for (User user : userService.findAll()) {
            result.add(userMapper.toDTO(user));
        }

        return result;
    }
}
