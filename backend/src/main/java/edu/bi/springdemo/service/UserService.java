package edu.bi.springdemo.service;

import edu.bi.springdemo.DTO.UserDTO;
import edu.bi.springdemo.entity.User;
import edu.bi.springdemo.exception.DuplicatedDataException;
import edu.bi.springdemo.exception.NotValidArgumentException;
import edu.bi.springdemo.exception.ResourceNotFoundException;
import edu.bi.springdemo.repository.UserRepository;
import edu.bi.springdemo.service.LoanService;
import edu.bi.springdemo.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanService loanService;
    private final ReviewService reviewService;
    private final PasswordEncoder passwordEncoder;

    private static final Integer DELETED_USER_ID = 9999;

    public UserService(UserRepository userRepository, LoanService loanService,
                       ReviewService reviewService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.loanService = loanService;
        this.reviewService = reviewService;
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

    @Transactional
    public void delete(Integer id){
        if (id.equals(DELETED_USER_ID)) {
            throw NotValidArgumentException.create("Cannot delete the system placeholder user");
        }

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("User with that id was not found"));

        User deletedUserPlaceholder = userRepository.findById(DELETED_USER_ID)
                .orElseThrow(() -> new IllegalStateException("System 'Deleted User' placeholder (ID 9999) is missing!"));

        loanService.reassignUserLoans(id, deletedUserPlaceholder);
        reviewService.reassignUserReviews(id, deletedUserPlaceholder);

        userRepository.delete(userToDelete);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> ResourceNotFoundException.create("User with username " + username + " was not found"));
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create("User with that id was not found"));
    }
}
