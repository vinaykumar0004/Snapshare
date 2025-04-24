package com.example.snapshare.network;

import com.example.snapshare.models.FaceRecognitionRequest;
import com.example.snapshare.models.FaceRecognitionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface
FaceRecognitionService {
    @POST("/face-recognition/identify")
    Call<FaceRecognitionResponse> identifyFace(@Body FaceRecognitionRequest request);
}

