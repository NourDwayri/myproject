package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewAvatarActivity extends AppCompatActivity {

    private EditText edit_name, edit_description, edit_image;
    private Button btn_save;
    private com.example.myapplication.StarbuzzDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_avatar);

        dbHelper = new com.example.myapplication.StarbuzzDatabaseHelper(this);

        edit_name = findViewById(R.id.edit_name);
        edit_description = findViewById(R.id.edit_description);
        edit_image = findViewById(R.id.edit_image); // optional: drawable name or leave empty
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(v -> {
            String name = edit_name.getText().toString().trim();
            String desc = edit_description.getText().toString().trim();
            String img = edit_image.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Please enter name and description", Toast.LENGTH_SHORT).show();
                return;
            }

            int drawableId = R.drawable.placeholder; // default
            // if user typed a drawable name and it exists, try to use it
            if (!img.isEmpty()) {
                int id = getResources().getIdentifier(img, "drawable", getPackageName());
                if (id != 0) drawableId = id;
            }

            SQLiteDatabase wdb = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("NAME", name);
            cv.put("DESCRIPTION", desc);
            cv.put("IMAGE_RESOURCE_ID", drawableId);
            long newId = wdb.insert("AVATAR", null, cv);
            wdb.close();

            Toast.makeText(this, "Saved (id=" + newId + ")", Toast.LENGTH_SHORT).show();

            // After save: go to RecordsListActivity showing all
            finish();
        });
    }
}
