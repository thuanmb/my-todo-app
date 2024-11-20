package com.thuanbui.todoapp;

import com.thuanbui.todoapp.enums.TodoStatus;
import com.thuanbui.todoapp.model.TodoItem;
import com.thuanbui.todoapp.model.User;
import com.thuanbui.todoapp.repository.TodoItemRepository;
import com.thuanbui.todoapp.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, TodoItemRepository todoItemRepository) {
        return args -> {
            // Create and save users
            User user1 = new User("alice", "password123");
            User user2 = new User("bob", "password456");
            userRepository.save(user1);
            userRepository.save(user2);

            // Create and save todo items
            TodoItem todo1 = new TodoItem("Finish project", TodoStatus.PENDING, user1);
            TodoItem todo2 = new TodoItem("Read a book", TodoStatus.IN_PROGRESS, user1);
            TodoItem todo3 = new TodoItem("Go shopping", TodoStatus.COMPLETED, user2);
            todoItemRepository.save(todo1);
            todoItemRepository.save(todo2);
            todoItemRepository.save(todo3);

            log.info("Data initialization complete.");
        };
    }
}
