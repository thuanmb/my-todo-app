package com.thuanbui.todoapp.controller;

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
        // Drop all tables to reset the database
        // jdbcTemplate.execute("DROP SCHEMA public CASCADE;");
        // jdbcTemplate.execute("CREATE SCHEMA public;");

        // Re-run Flyway migrations
        // flyway.clean();
        // flyway.migrate();
    }

    @Test
    public void testGetTodos() throws Exception {
        // Your test code here
        // mockMvc.perform(get("/api/todos"))
        // .andExpect(status().isOk())
        // .andExpect(content().contentType("application/json"));
    }

}
