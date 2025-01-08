package com.thuanbui.todoapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thuanbui.todoapp.dto.CreateUserRequestDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setupDatabase() {
        // Cleaning the tables and insert the data
        jdbcTemplate.execute("TRUNCATE TABLE users CASCADE;");

        // user's password is: password123
        jdbcTemplate.execute(
                """
                        INSERT INTO users(username, password, active, deleted) VALUES
                        ('username1', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', TRUE, TRUE),
                        ('username1', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', TRUE, FALSE); """);
    }

    @Test
    public void testCreateNewUserWhenSuccess() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setUsername("username2");
        request.setPassword("password123");

        mockMvc.perform(post("/api/v0/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.username").value("username2"))
                .andExpect(jsonPath("$.data.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.data.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.data.active").value(true))
                .andExpect(jsonPath("$.data.deleted").value(false));
    }

    @Test
    public void testCreateNewUserWhenUserExist() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setUsername("username1");
        request.setPassword("password123");

        mockMvc.perform(post("/api/v0/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("User with username 'username1' already exists."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
