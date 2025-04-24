package com.example.snapshare.network;

import com.example.snapshare.models.NotificationRequest;
import com.example.snapshare.models.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface NotificationService {
    @POST("/notifications/send")
    Call<NotificationResponse> sendNotification(@Body NotificationRequest request);
}

