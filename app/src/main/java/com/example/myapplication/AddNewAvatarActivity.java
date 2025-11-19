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

    private EditText editName, editDescription, editImage;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_avatar);

        editName = findViewById(R.id.edit_name);
        editDescription = findViewById(R.id.edit_description);
        editImage = findViewById(R.id.edit_image); // Optional: URL
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String desc = editDescription.getText().toString().trim();
            String imgUrl = editImage.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Please enter name and description", Toast.LENGTH_SHORT).show();
                return;
            }

            // Default placeholder if no URL provided
            if (imgUrl.isEmpty()) {
                imgUrl = "https://via.placeholder.com/150";
            }

            AvatarDto newAvatar = new AvatarDto(name, desc, imgUrl);

            RetrofitClient.getAvatarApiService().addAvatar(newAvatar)
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if(response.isSuccessful() && response.body() != null) {
                                Toast.makeText(AddNewAvatarActivity.this, "Saved to server!", Toast.LENGTH_SHORT).show();
                                finish(); // Close activity after save
                            } else {
                                Toast.makeText(AddNewAvatarActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Toast.makeText(AddNewAvatarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
