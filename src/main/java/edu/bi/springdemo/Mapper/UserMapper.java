package edu.bi.springdemo.Mapper;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword(user.getPassword()); //comment to not show password to the user
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        return user;
    }
}
