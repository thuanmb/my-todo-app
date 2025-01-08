package com.thuanbui.todoapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thuanbui.todoapp.exceptions.UserExistsException;
import com.thuanbui.todoapp.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setupDatabase() {
        // Cleaning the tables and insert the data
        jdbcTemplate.execute("TRUNCATE TABLE users CASCADE;");

        // user's password is: password123
        jdbcTemplate.execute(
                """
                        INSERT INTO users(username, password, active, deleted) VALUES
                        ('username1', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', TRUE, TRUE),
                        ('username1', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', TRUE, FALSE),
                        ('username3', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', FALSE, TRUE),
                        ('username4', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', TRUE, TRUE),
                        ('username5', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', FALSE, FALSE); """);

    }

    @Test
    public void testCreateNewUserWhenUserDoesExistAndActive() throws Exception {
        UserExistsException exception = assertThrows(
                UserExistsException.class,
                () -> userService.createNewUser("username1", "password123"));

        assertEquals("User with username 'username1' already exists.", exception.getMessage());
        assertEquals(2, userRepository.countByUsername("username1"));
    }

    @Test
    public void testCreateNewUserWhenUserDoesExistAndInactive() throws Exception {
        UserExistsException exception = assertThrows(
                UserExistsException.class,
                () -> userService.createNewUser("username5", "password123"));

        assertEquals("User with username 'username5' already exists.", exception.getMessage());
        assertEquals(1, userRepository.countByUsername("username5"));
    }

    @Test
    public void testCreateNewUserWhenUserDoesExistDeletedAndInactive() throws Exception {
        var newUser3 = userService.createNewUser("username3", "newPassword3");
        assertTrue(newUser3 != null);
        assertEquals("username3", newUser3.getUsername());
        assertEquals(true, newUser3.getActive());
        assertEquals(false, newUser3.getDeleted());
        assertEquals(true, newUser3.verifyPassword("newPassword3"));
        assertEquals(2, userRepository.countByUsername("username3"));
        assertTrue(newUser3.getCreatedAt() != null);
        assertTrue(newUser3.getUpdatedAt() != null);
    }

    @Test
    public void testCreateNewUserWhenUserDoesExistButDeleted() throws Exception {
        var newUser4 = userService.createNewUser("username4", "newPassword4");
        assertTrue(newUser4 != null);
        assertEquals("username4", newUser4.getUsername());
        assertEquals(true, newUser4.getActive());
        assertEquals(false, newUser4.getDeleted());
        assertEquals(true, newUser4.verifyPassword("newPassword4"));
        assertEquals(2, userRepository.countByUsername("username4"));
    }

    @Test
    public void testCreateNewUserWhenUserDoesNotExist() throws Exception {
        var newUser = userService.createNewUser("username10", "newPassword10");
        assertTrue(newUser != null);
        assertEquals("username10", newUser.getUsername());
        assertEquals(true, newUser.getActive());
        assertEquals(false, newUser.getDeleted());
        assertEquals(true, newUser.verifyPassword("newPassword10"));
        assertEquals(1, userRepository.countByUsername("username10"));
    }

}
