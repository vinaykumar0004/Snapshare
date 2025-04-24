//package com.example.snapshare.models;
//
//public class LoginRequest {
//
//    private String email;
//    private String password;
//
//    public LoginRequest(String email, String password) {
//        if (email == null || password == null) {
//            throw new IllegalArgumentException("Email and password cannot be null");
//        }
//        this.email = email;
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
package com.example.snapshare.models;

public class LoginRequest {
    private String email;
    private String password;
    private String specificCode;

    public LoginRequest(String email, String password, String specificCode) {
        this.email = email;
        this.password = password;
        this.specificCode = specificCode;
    }

    // Getters and setters
}

