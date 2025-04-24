package com.example.snapshare.models;

import java.util.List;

public class PhotoUploadRequest {
    private String userId;
    private String url;
    private String description;

    private String passcode;
    private List<String> photos;

    // Constructor, getters and setters

    public PhotoUploadRequest(String userId, String url, String description) {
        this.userId = userId;
        this.url = url;
        this.description = description;
    }
}

