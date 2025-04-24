package com.example.snapshare.network;

import com.example.snapshare.models.Photo;
import com.example.snapshare.models.PhotoListResponse;
import com.example.snapshare.models.PhotoUploadRequest;
import com.example.snapshare.models.PhotoUploadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PhotoService {
    @POST("/photos/upload")
    Call<PhotoUploadResponse> uploadPhoto(@Body PhotoUploadRequest photoUploadRequest);

    @GET("/photos/user/{userId}")
    Call<List<Photo>> getPhotosByUserId(@Path("userId") String userId);

    @POST("getPhotos")
    Call<PhotoListResponse> getPhotos();
}

