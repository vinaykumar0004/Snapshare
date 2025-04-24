package com.example.snapshare.network;

import com.example.snapshare.models.PasscodeCreateRequest;
import com.example.snapshare.models.PasscodeCreateResponse;
import com.example.snapshare.models.PasscodeValidationRequest;
import com.example.snapshare.models.PasscodeValidationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface PasscodeService {
    @POST("/passcodes/validate")
    Call<PasscodeValidationResponse> validatePasscode(@Body PasscodeValidationRequest request);

    @POST("/passcodes/create")
    Call<PasscodeCreateResponse> createPasscode(@Body PasscodeCreateRequest request);
}
