package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AvatarCursorAdapter extends CursorAdapter {

    public AvatarCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_avatar, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.text_name);
        TextView description = view.findViewById(R.id.text_description);
        ImageView image = view.findViewById(R.id.image_avatar);

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow("NAME")));
        description.setText(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION")));

        int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("IMAGE_RESOURCE_ID"));
        if (imageResId != 0) {
            image.setImageResource(imageResId);
        } else {
            image.setImageResource(R.drawable.placeholder); // optional placeholder
        }
    }
}
