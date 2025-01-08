package com.thuanbui.todoapp.services;

import com.thuanbui.todoapp.exceptions.UserExistsException;
import com.thuanbui.todoapp.model.User;
import com.thuanbui.todoapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public User createNewUser(String username, String password) throws UserExistsException {
        // step 1: check if user exist or not
        var existingUser = userRepository.findUserByUsername(username);
        if (existingUser != null) {
            throw new UserExistsException("User with username '" + username + "' already exists.");
        }

        // step 2: create the new user
        var newUser = new User(username, password);
        try {
            userRepository.save(newUser);
        } catch (OptimisticLockException e) {
            // Consider adding logic to handle retries or notify the user about the conflict
        } finally {
            // Refresh the entity and potentially retry the update
            entityManager.refresh(newUser);
        }

        // step 3: assign the user into the default role
        return newUser;
    }
}
