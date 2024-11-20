package com.thuanbui.todoapp.repository;

import com.thuanbui.todoapp.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
