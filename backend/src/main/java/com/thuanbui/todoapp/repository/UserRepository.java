package com.thuanbui.todoapp.repository;

import com.thuanbui.todoapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deleted = false")
    User findUserByUsername(@Param("username") String username);

    int countByUsername(String username);
}
