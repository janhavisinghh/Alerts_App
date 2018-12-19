package com.example.android.exampleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.exampleapp.databinding.NotificationItemBinding;

import java.util.ArrayList;

import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_CATEGORY;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TIME_STAMP;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TITLE;
import static com.example.android.exampleapp.DataContract.DataEntry.CONTENT_URI;
import static com.example.android.exampleapp.DataContract.DataEntry.TABLE_NAME;
import static com.example.android.exampleapp.DataContract.DataEntry._ID;
import static com.example.android.exampleapp.DataContract.DataEntry.buildTodoUriWithId;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryOneViewHolder> {
    public ArrayList<DataObjs> data;
    private Context context;
    private NotificationItemBinding binding;
    private Cursor cursor;

    public CategoryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public CategoryAdapter.CategoryOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_item, parent, false);
        binding = NotificationItemBinding.bind(view);

        return new CategoryAdapter.CategoryOneViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryOneViewHolder holder,final int position) {
        cursor.moveToPosition(position);

        final String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        final String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
        final String time_stamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_STAMP));
        final int id = cursor.getInt(cursor.getColumnIndex(_ID));

        binding.textView.setText(title);

        binding.closeButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchDataInDB(title)){
                    removeData(title);
                }
            }
        });
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class CategoryOneViewHolder extends RecyclerView.ViewHolder {
        /**
         * @param itemView
         */
        public CategoryOneViewHolder(View itemView) {
            super(itemView);
        }

    }
    public boolean searchDataInDB(String title) {
        String[] projection = {
                COLUMN_TITLE,
                COLUMN_CATEGORY,
                COLUMN_TIME_STAMP,
        };
        String selection = COLUMN_TITLE + " =?";
        String[] selectionArgs = {title};
        String limit = "1";
        long id = 2;

        Cursor cursor = context.getContentResolver().query(buildTodoUriWithId(id),
                projection,
                selection,
                selectionArgs,
                limit);
        boolean data_present = (cursor.getCount() > 0);
        cursor.close();
        return data_present;
    }
    private void removeData(final String title) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        context.getContentResolver().delete(CONTENT_URI, COLUMN_TITLE + "=?", new String[]{title});

    }
    public Cursor getAllData(String category) {
        String selection = COLUMN_CATEGORY + " =?";
        String[] selectionArgs = {category};
        return context.getContentResolver().query(CONTENT_URI,
                null,
                selection,
                selectionArgs,
                _ID);
    }
}
