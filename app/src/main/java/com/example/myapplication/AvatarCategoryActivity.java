package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listAvatars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_category);

        listAvatars = findViewById(R.id.list_avatars);
        Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AvatarCategoryActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // 1️⃣ Load SQLite avatars first
        loadSQLiteAvatars();

        // 2️⃣ Fetch network avatars with Retrofit
        fetchNetworkAvatars();
    }

    private void loadSQLiteAvatars() {
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("AVATAR",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);

                listAvatars.setAdapter(listAdapter);
            } else {
                Toast.makeText(this, "No local avatars found", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        listAvatars.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(AvatarCategoryActivity.this, AvatarActivity.class);
            intent.putExtra(AvatarActivity.EXTRA_AVATARID, id);
            startActivity(intent);
        });
    }

    private void fetchNetworkAvatars() {
        AvatarApiService api = RetrofitClient.getAvatarApiService();
        api.getAllAvatars().enqueue(new Callback<List<AvatarDto>>() {
            @Override
            public void onResponse(Call<List<AvatarDto>> call, Response<List<AvatarDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AvatarDto> networkAvatars = response.body();
                    displayNetworkAvatars(networkAvatars);
                }
            }

            @Override
            public void onFailure(Call<List<AvatarDto>> call, Throwable t) {
                Toast.makeText(AvatarCategoryActivity.this, "Failed to fetch network avatars", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayNetworkAvatars(List<AvatarDto> avatars) {
        // Convert network data to simple ArrayList<String> for ListView display
        List<String> avatarNames = new ArrayList<>();
        for (AvatarDto a : avatars) {
            avatarNames.add(a.getName());
        }

        // Update ListView with network avatars
        listAvatars.setAdapter(new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                avatarNames));

        // Click listener for network avatars (can customize)
        listAvatars.setOnItemClickListener((parent, view, position, id) -> {
            AvatarDto selected = avatars.get(position);
            Toast.makeText(this, "Selected: " + selected.getName(), Toast.LENGTH_SHORT).show();
            // You can later open AvatarActivity or map to local Avatar object
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
        if (db != null) db.close();
    }
}
