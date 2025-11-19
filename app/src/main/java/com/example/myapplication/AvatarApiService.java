package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AvatarApiService {

    @GET("avatars")
    Call<List<AvatarDto>> getAllAvatars();

    @POST("avatars")
    Call<ApiResponse<AvatarDto>> addAvatar(@Body AvatarDto avatar);

}
