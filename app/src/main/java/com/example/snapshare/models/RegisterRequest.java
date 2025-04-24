// src/main/java/com/example/snapshare/RegisterRequest.java
package com.example.snapshare.models;


public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String specificCode;

    public RegisterRequest(String name, String email, String password, String specificCode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.specificCode = specificCode;
    }

    // Getters and setters
}

