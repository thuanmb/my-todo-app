package com.thuanbui.todoapp.dto;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.thuanbui.todoapp.model.User;

public class UserDTO {
    private Long id;
    private String username;
    private String createdAt;
    private String updatedAt;
    private boolean active;
    private boolean deleted;

    public static UserDTO fromEntity(User user) {
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setActive(user.getActive());
        dto.setDeleted(user.getDeleted());
        dto.setCreatedAt(Optional.ofNullable(user.getCreatedAt())
                .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).orElse(null));
        dto.setUpdatedAt(Optional.ofNullable(user.getUpdatedAt())
                .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).orElse(null));
        return dto;
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
