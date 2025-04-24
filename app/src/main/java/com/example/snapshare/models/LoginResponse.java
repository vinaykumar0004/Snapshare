// src/main/java/com/example/snapshare/LoginResponse.java
package com.example.snapshare.models;

import com.example.snapshare.models.User;

public class LoginResponse {

    private boolean success;
    private String message;
    private String userType;
    private boolean isPhotographer;


    private String token;



    public LoginResponse(boolean success, boolean isPhotographer , String message, String userType) {
        this.success = success;
        this.isPhotographer = isPhotographer;
        this.message = message;
        this.userType = userType;
    }

//    public LoginResponse(boolean success, boolean isPhotographer) {
//        this.success = success;
//        this.isPhotographer = isPhotographer;
//    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // Example of a validation method, adjust as per your logic
    public boolean isValid() {
        return success;
    }

    // Define the User class or import if it exists elsewhere
    public static class User {
        private String name;
        private String email;

        // Constructor
        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        // Getter for name
        public String getName() {
            return name;
        }

        // Setter for name
        public void setName(String name) {
            this.name = name;
        }

        // Getter for email
        public String getEmail() {
            return email;
        }

        // Setter for email
        public void setEmail(String email) {
            this.email = email;
        }
    }
}