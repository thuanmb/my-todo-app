package com.thuanbui.todoapp.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "password")
    private String passwordHash;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    // Constructor
    public User() {
    }

    public User(String username, String plainPassword) {
        this.username = username;
        this.passwordHash = hashPassword(plainPassword);
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    // Password hash is not exposed publicly
    private String getPasswordHash() {
        return passwordHash;
    }

    // Hashes a plain text password using BCrypt
    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // Sets a new password after hashing it
    public void setPassword(String plainPassword) {
        this.passwordHash = hashPassword(plainPassword);
    }

    // Verifies the password by comparing a plain password to the stored hash
    public boolean verifyPassword(String plainPassword) {
        return passwordEncoder.matches(plainPassword, this.passwordHash);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username);
    }

    @Override
    public String toString() {
        return String.format(
                "User{id=%d, username='%s', created_at=%s}",
                this.id,
                this.username,
                this.createdAt);
    }
}
