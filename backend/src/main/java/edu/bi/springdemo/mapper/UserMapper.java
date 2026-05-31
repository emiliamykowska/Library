package edu.bi.springdemo.mapper;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        return user;
    }
}
