package com.example.snapshare.models;

public class FaceRecognitionRequest {
    private String userId;
    private String imageUrl;
    private String photo;

    // Constructor, getters and setters

    public FaceRecognitionRequest(String userId, String imageUrl) {
        this.userId = userId;
        this.imageUrl = imageUrl;
    }
}
