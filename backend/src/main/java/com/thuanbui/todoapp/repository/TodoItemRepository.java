package com.thuanbui.todoapp.repository;

import com.thuanbui.todoapp.model.TodoItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
