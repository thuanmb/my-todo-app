package com.thuanbui.todoapp.controller;

import com.thuanbui.todoapp.dto.CreateUserRequestDTO;
import com.thuanbui.todoapp.dto.UserDTO;
import com.thuanbui.todoapp.exceptions.UserExistsException;
import com.thuanbui.todoapp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v0/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createNewUser(@Valid @RequestBody CreateUserRequestDTO userRequest) {
        try {
            // step 1: create the new user in the db
            String username = userRequest.getUsername();
            String password = userRequest.getPassword();
            var newUser = userService.createNewUser(username, password);

            // step 2: return the result
            return ResponseEntity.ok(new ApiResponse<>(UserDTO.fromEntity(newUser)));
        } catch (UserExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(e.getMessage()));
        }
    }
}
