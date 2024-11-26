package com.thuanbui.todoapp.repository;

import com.thuanbui.todoapp.model.TodoItem;
import com.thuanbui.todoapp.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    Page<TodoItem> findByUser(User user, Pageable pageable);
}
