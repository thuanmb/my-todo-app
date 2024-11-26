package com.thuanbui.todoapp.dto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.thuanbui.todoapp.enums.TodoStatus;
import com.thuanbui.todoapp.model.TodoItem;

public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private TodoStatus status;
    private String createdAt;

    public static TodoDTO fromEntity(TodoItem todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        dto.setCreatedAt(Optional.ofNullable(todo.getCreatedAt())
                .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).orElse(null));
        return dto;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TodoStatus getStatus() {
        return this.status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
