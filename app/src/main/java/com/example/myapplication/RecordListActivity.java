package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordListActivity extends AppCompatActivity {

    private StarbuzzDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listAvatars;
    private TextView tv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);

        dbHelper = new StarbuzzDatabaseHelper(this);
        listAvatars = findViewById(R.id.list_avatars);
        tv_empty = findViewById(R.id.tv_empty);

        String queryType = getIntent().getStringExtra("query_type");
        if (queryType == null) queryType = "all";

        try {
            db = dbHelper.getReadableDatabase();

            if (queryType.equals("all")) {
                cursor = db.query("AVATAR",
                        new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                        null, null, null, null, null);
            } else {
                String q = getIntent().getStringExtra("query_value");
                cursor = db.query("AVATAR",
                        new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                        "NAME = ?",
                        new String[]{q},
                        null, null, null);
            }

            if (cursor == null || cursor.getCount() == 0) {
                tv_empty.setText("No results were found!");
                tv_empty.setVisibility(TextView.VISIBLE);
                listAvatars.setVisibility(ListView.GONE);
            } else {
                tv_empty.setVisibility(TextView.GONE);
                listAvatars.setVisibility(ListView.VISIBLE);

                // Use custom adapter instead of SimpleCursorAdapter
                AvatarCursorAdapter adapter = new AvatarCursorAdapter(this, cursor, 0);
                listAvatars.setAdapter(adapter);

                listAvatars.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
                    Intent i = new Intent(RecordListActivity.this, AvatarActivity.class);
                    i.putExtra(AvatarActivity.EXTRA_AVATARID, id);
                    startActivity(i);
                });
            }

        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        // Back button
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RecordListActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
        if (db != null) db.close();
    }
}
