package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AvatarApiService {

    // Example endpoint to get all avatars
    @GET("/avatars")
    Call<List<AvatarDto>> getAllAvatars();

}
