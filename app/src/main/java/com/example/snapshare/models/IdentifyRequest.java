package com.example.snapshare.models;

//public class IdentifyRequest {
//    private String email;
//    private String identificationCode;
//
//    public IdentifyRequest(String email, String identificationCode) {
//        this.email = email;
//        this.identificationCode = identificationCode;
//    }
//
//    // getters and setters
//}

public class IdentifyRequest {
    private String email;

    public IdentifyRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


