package edu.bi.springdemo.service;

import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.DuplicatedDataException;
import edu.bi.springdemo.exception.NotValidArgumentException;
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
}
