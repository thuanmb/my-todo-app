package com.thuanbui.todoapp.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.thuanbui.todoapp.enums.TodoStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING) // Store the enum value as a string in the database
    @Column(name = "status", nullable = false)
    private TodoStatus status;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TodoItem() {
    }

    public TodoItem(String title, TodoStatus status, User user) {
        this.title = title;
        this.user = user;
        this.status = status;
    }

    // Getters and setters
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

    public User getUser() {
        return this.user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TodoItem))
            return false;
        TodoItem todoItem = (TodoItem) o;
        return Objects.equals(this.id, todoItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description);
    }

    @Override
    public String toString() {
        return String.format(
                "TodoItem{id=%d, title='%s', description='%s', status=%s, created_at=%s, user=%s}",
                this.id,
                this.title,
                this.description,
                this.status,
                this.createdAt,
                this.user);
    }
}
