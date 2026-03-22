package edu.bi.springdemo.entity;

import edu.bi.springdemo.enums.UserRole;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    private String name;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
