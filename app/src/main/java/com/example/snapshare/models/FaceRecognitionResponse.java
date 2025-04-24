package com.example.snapshare.models;

import com.example.snapshare.models.Photo;

import java.util.List;

public class FaceRecognitionResponse {
    private String status;
    private String message;
    private List<Photo> matchedPhotos;
    // Getters and setters

    private List<String> photoUrls;
    // Getters and setters
}

