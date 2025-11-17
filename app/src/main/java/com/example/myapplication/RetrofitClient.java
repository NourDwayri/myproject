package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static AvatarApiService getAvatarApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    // ✅ Use your actual MockAPI base URL ending with /
                    .baseUrl("https://691ac0712d8d7855757001df.mockapi.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AvatarApiService.class);
    }
}
