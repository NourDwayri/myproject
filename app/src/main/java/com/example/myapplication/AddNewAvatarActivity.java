package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAvatarActivity extends AppCompatActivity {

    private EditText edit_name, edit_description, edit_image;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_avatar);

        edit_name = findViewById(R.id.edit_name);
        edit_description = findViewById(R.id.edit_description);
        edit_image = findViewById(R.id.edit_image);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(v -> {
            String name = edit_name.getText().toString().trim();
            String desc = edit_description.getText().toString().trim();
            String imgUrl = edit_image.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Please enter name and description", Toast.LENGTH_SHORT).show();
                return;
            }

            AvatarDto newAvatar = new AvatarDto(name, desc, imgUrl);

            // Call backend to add avatar
            RetrofitClient.getAvatarApiService().addAvatar(newAvatar)
                    .enqueue(new Callback<AvatarDto>() { // Use AvatarDto instead of ApiResponse
                        @Override
                        public void onResponse(Call<AvatarDto> call, Response<AvatarDto> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(AddNewAvatarActivity.this, "Avatar added!", Toast.LENGTH_SHORT).show();
                                finish(); // Close activity after success
                            } else {
                                Toast.makeText(AddNewAvatarActivity.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AvatarDto> call, Throwable t) {
                            Toast.makeText(AddNewAvatarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
