package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarActivity extends Activity {

    public static final String EXTRA_AVATARID = "avatarId";

    private TextView name, description;
    private ImageView photo;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        photo = findViewById(R.id.photo);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AvatarActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Load avatar from backend
        long avatarId = getIntent().getLongExtra(EXTRA_AVATARID, 0);
        loadAvatarFromServer(avatarId);
    }

    private void loadAvatarFromServer(long avatarId) {
        RetrofitClient.getAvatarApiService().getAllAvatars().enqueue(new Callback<List<AvatarDto>>() {
            @Override
            public void onResponse(Call<List<AvatarDto>> call, Response<List<AvatarDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AvatarDto> avatars = response.body();

                    // Find avatar by id (or index, assuming id = position)
                    if (avatarId >= 0 && avatarId < avatars.size()) {
                        AvatarDto avatar = avatars.get((int) avatarId);
                        displayAvatar(avatar);
                    } else {
                        Toast.makeText(AvatarActivity.this, "Avatar not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AvatarActivity.this, "Failed to load avatars", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AvatarDto>> call, Throwable t) {
                Toast.makeText(AvatarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAvatar(AvatarDto avatar) {
        name.setText(avatar.getName());
        description.setText(avatar.getDescription());

        if (avatar.getImageUrl() != null && !avatar.getImageUrl().isEmpty()) {
            // Use Glide or Picasso to load images from URL
            // Example with Glide:
            // Glide.with(this).load(avatar.getImageUrl()).into(photo);
            photo.setVisibility(ImageView.VISIBLE);
        } else {
            photo.setVisibility(ImageView.GONE);
        }
    }
}
