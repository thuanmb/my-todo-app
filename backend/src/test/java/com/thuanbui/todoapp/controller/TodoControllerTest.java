package com.thuanbui.todoapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setupDatabase() {
        // Re-run Flyway migrations
        // flyway.clean();
        // flyway.migrate();

        // Cleaning the tables and insert the data
        jdbcTemplate.execute("TRUNCATE TABLE users, todos CASCADE;");

        // user's password is: password123
        jdbcTemplate.execute(
                """
                        INSERT INTO users(id, username, password, created_at) VALUES
                        (1, 'username1', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', '2024-01-01 00:00:00'),
                        (2, 'username2', '$2a$10$XyxjhkJrhHugma1krJEjbOiUp5xrvuXhG3.y691/PMkCSYVhbBy7i', '2024-01-02 00:00:00'); """);
        jdbcTemplate.execute(
                """
                        INSERT INTO todos(id, user_id, title, description, status, due_date, created_at) VALUES
                        (11, 1, 'TODO title 1', 'TODO description 1', 'PENDING', '2025-01-01 00:00:00', '2024-01-01 00:00:00'),
                        (12, 1, 'TODO title 2', 'TODO description 2', 'IN_PROGRESS', '2024-03-01 00:00:00', '2024-01-02 00:00:00'),
                        (13, 2, 'TODO title 3', 'TODO description 3', 'COMPLETED', '2024-08-01 00:00:00', '2024-01-03 00:00:00'),
                        (14, 2, 'TODO title 4', 'TODO description 4', 'PENDING', '2025-02-01 00:00:00', '2024-01-05 00:00:00');""");

    }

    @Test
    public void testGetTodosWhenUserHasTodos() throws Exception {
        mockMvc.perform(get("/v0/todos/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.length()").value(2))
                // Assert properties of the first item in the array
                .andExpect(jsonPath("$.data[0].id").value(11))
                .andExpect(jsonPath("$.data[0].title").value("TODO title 1"))
                // Assert properties of the second item in the array
                .andExpect(jsonPath("$.data[1].id").value(12))
                .andExpect(jsonPath("$.data[1].title").value("TODO title 2"));
    }

    @Test
    public void testGetTodosWhenUserNotFound() throws Exception {
        mockMvc.perform(get("/v0/todos/user/123"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.error").value("User with ID 123 not found."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
