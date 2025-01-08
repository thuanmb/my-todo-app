package com.thuanbui.todoapp.controller;

import java.util.Optional;

import com.thuanbui.todoapp.dto.TodoDTO;
import com.thuanbui.todoapp.model.TodoItem;
import com.thuanbui.todoapp.model.User;
import com.thuanbui.todoapp.repository.TodoItemRepository;
import com.thuanbui.todoapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/todos")
public class TodoItemController {
    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getTodosByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(String.format("User with ID %d not found.", userId)));
        }

        Page<TodoItem> todoPage = todoItemRepository.findByUser(user.get(),
                PageRequest.of(page, size, Sort.by("createdAt").ascending()));
        return ResponseEntity.ok(new ApiResponse<>(todoPage.map(TodoDTO::fromEntity).toList()));
    }
    // end::get-aggregate-root[]
}
