package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edit_search;
    private Button btn_go, btn_show_all, btn_add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_search = findViewById(R.id.edit_search);
        btn_go = findViewById(R.id.btn_go);
        btn_show_all = findViewById(R.id.btn_show_all);
        btn_add_new = findViewById(R.id.btn_add_new);

        btn_show_all.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RecordListActivity.class);
            i.putExtra("query_type", "all");
            startActivity(i);
        });

        btn_add_new.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddNewAvatarActivity.class);
            startActivity(i);
        });

        btn_go.setOnClickListener(v -> {
            String q = edit_search.getText().toString().trim();
            if (q.isEmpty()) {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(MainActivity.this, RecordListActivity.class);
            i.putExtra("query_type", "search");
            i.putExtra("query_value", q);
            startActivity(i);
        });
    }
}
