package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static AvatarApiService getAvatarApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://myproject-jxip9iiux-nour-dwayris-projects.vercel.app/") // ✅ deployed FastAPI
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AvatarApiService.class);
    }
}
