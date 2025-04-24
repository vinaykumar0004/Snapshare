package com.example.snapshare.models;

public class PasscodeValidationRequest {
    private String userId;
    private String passcode;

    // Constructor, getters and setters

    public PasscodeValidationRequest(String userId, String passcode) {
        this.userId = userId;
        this.passcode = passcode;
    }
}

