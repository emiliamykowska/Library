package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.DuplicatedDataException;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional //use annotation from spring, it's a transaction
    public User save(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw DuplicatedDataException.create("Email already exists");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw DuplicatedDataException.create("Username already exists");
        }

        String password = user.getPassword();

        if (password.length() < 6){
            throw NotValidArgumentException.create("Password has to be at least 6 characters long");
        }

        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User update(Integer id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("User with that id was not found"));

        if (userDTO.getEmail() != null) {
            var existingUserEmail = userRepository.findByEmail(userDTO.getEmail()); //all ids with such email

            if (existingUserEmail.isPresent() && !existingUserEmail.get().getUserId().equals(id)) { //check if id of the user with that email is the same as user's that is being updated
                throw DuplicatedDataException.create("Email already exists");
            }

            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getUsername() != null) {
            var existingUserUsername = userRepository.findByUsername(userDTO.getUsername());

            if (existingUserUsername.isPresent() && !existingUserUsername.get().getUserId().equals(id)) {
                throw DuplicatedDataException.create("Username already exists");
            }

            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getPassword() != null) {
            if (userDTO.getPassword().length() < 6){
                throw NotValidArgumentException.create("Password must be at least 6 characters");
            }

            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }

        return userRepository.save(user);
    }

    public void delete(Integer id){
        if (!userRepository.existsById(id)){
            throw ResourceNotFoundException.create("User with that id was not found");
        }

        userRepository.deleteById(id);
    }
}
