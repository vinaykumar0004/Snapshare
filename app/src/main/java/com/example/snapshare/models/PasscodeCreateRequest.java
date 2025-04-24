package com.example.snapshare.models;

public class PasscodeCreateRequest {
    private String userId;
    private String passcode;

    // Constructor, getters and setters

    public PasscodeCreateRequest(String userId, String passcode) {
        this.userId = userId;
        this.passcode = passcode;
    }
}
