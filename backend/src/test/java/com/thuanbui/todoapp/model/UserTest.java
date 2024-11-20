package com.thuanbui.todoapp.model;

public class UserTest {
    public static void main(String[] args) {
        // Create a new user with username and password
        User user = new User("exampleUser", "securePassword");

        // Verify the password
        System.out.println("Password verification (correct): " + user.verifyPassword("securePassword"));
        System.out.println("Password verification (incorrect): " + user.verifyPassword("wrongPassword"));

        // Change the password
        user.setPassword("newSecurePassword");
        System.out.println("Password verification after update (correct): " + user.verifyPassword("newSecurePassword"));
    }
}
