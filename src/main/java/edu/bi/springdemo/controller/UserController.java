package edu.bi.springdemo.controller;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.Mapper.UserMapper;
import edu.bi.springdemo.Service.UserService;
import edu.bi.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody User addUser(@RequestBody UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        return userService.save(user);
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAllReviews(){
        return userService.findAll();
    }
}
