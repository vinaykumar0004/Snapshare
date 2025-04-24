package com.example.snapshare.network;


import com.example.snapshare.models.IdentifyRequest;
import com.example.snapshare.models.IdentifyResponse;
import com.example.snapshare.models.LoginRequest;
import com.example.snapshare.models.LoginResponse;
import com.example.snapshare.models.Photo;
import com.example.snapshare.models.PhotoUploadResponse;
import com.example.snapshare.models.RegisterRequest;
import com.example.snapshare.models.RegisterResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/auth/identifyUser")
    Call<IdentifyResponse> identifyUser(@Body IdentifyRequest identifyRequest);

    @POST("/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/auth/send-verification-email")
    Call<RegisterResponse> sendVerificationEmail(@Body Map<String, String> email);  // Assuming email is sent as a JSON body

    @POST("/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @Multipart
    @POST("/photo/upload-receiver-photo-gallery")
    Call<PhotoUploadResponse> uploadReceiverPhotoGallery(
            @Part MultipartBody.Part photo,
//            @Part("name") RequestBody name,
//            @Part("email") RequestBody email,
            @Part("specificCode") RequestBody specificCode
    );

    @GET("/photographer/get-photos")
    Call<List<Photo>> getPhotos(@Query("specificCode") String specificCode);

//    @Multipart
//    @POST("/photo/upload-receiver-photo-camera")
//    Call<PhotoUploadResponse> uploadReceiverPhotoCamera(
//            @Part MultipartBody.Part photo,
////            @Part("name") RequestBody name,
////            @Part("email") RequestBody email,
//            @Part("specificCode") RequestBody specificCode
//    );
    // Define the /upload-photos endpoint
    @Multipart
    @POST("/photo/upload-photos")
    Call<ResponseBody>uploadPhotos(
            @Part("specificCode") RequestBody specificCode,
            @Part List<MultipartBody.Part> photos,
            @Part("embeddings") RequestBody embeddings
    );

    // Define the /upload-google-drive-photos endpoint
    @Multipart
    @POST("/photo/upload-google-drive-photos")
    Call<PhotoUploadResponse> uploadGoogleDrivePhotos(
            @Part("specificCode") RequestBody specificCode,
            @Part("photos") RequestBody photos
    );

//    @GetMapping("/photo/get-photos")
//    public List<Photo> getPhotos(@RequestParam String specificCode) {
//        return photoRepository.findBySpecificCode(specificCode);

//    @GET("/photo/get-photos")
//    Call<List<Photo>> getPhotosBySpecificCode(@Query("specificCode") String specificCode);
//    @GET("photos") // Adjust the endpoint as per your API
//    Call<List<Photo>> getPhotos(@Query("userId") String userId);



}




