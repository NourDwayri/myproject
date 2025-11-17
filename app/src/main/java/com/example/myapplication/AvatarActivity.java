package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AvatarActivity extends Activity {

    public static final String EXTRA_AVATARID = "avatarId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        long drinkId = getIntent().getLongExtra(EXTRA_AVATARID, 0);

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        Cursor cursor = null;
        SQLiteDatabase db = null;

        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();

            cursor = db.query(
                    "DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{String.valueOf(drinkId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {

                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                ImageView photo = findViewById(R.id.photo);

                if (photoId != 0) {
                    // Show image if resource ID is valid
                    photo.setImageResource(photoId);
                    photo.setContentDescription(nameText);
                    photo.setVisibility(ImageView.VISIBLE);
                } else {
                    // Hide ImageView if no image
                    photo.setVisibility(ImageView.GONE);
                }
            } else {
                Toast.makeText(this, "Avatar not found", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        // Back button
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AvatarActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
