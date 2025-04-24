// src/main/java/com/example/snapshare/RegisterResponse.java
package com.example.snapshare.models;

import com.google.gson.annotations.SerializedName;


public class RegisterResponse {

    private boolean registered;
//    private String message;
    private boolean isPhotographer;

    private String token;

    private boolean success;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

        @Override
        public String toString() {
            return "RegisterResponse{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }


    public RegisterResponse(boolean success,boolean isPhotographer, boolean registered, String message) {
        this.success = success;
        this.isPhotographer = isPhotographer;
        this.registered = registered;
        this.message = message;
    }


    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

}

