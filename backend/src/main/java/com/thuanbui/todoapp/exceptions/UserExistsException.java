package com.thuanbui.todoapp.exceptions;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
