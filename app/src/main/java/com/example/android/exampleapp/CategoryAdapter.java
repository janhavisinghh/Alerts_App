package com.example.android.exampleapp;

import android.content.Context;
import android.database.Cursor;
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
import static com.example.android.exampleapp.DataContract.DataEntry.TABLE_NAME;

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

        binding.textView.setText(title);
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
        int a = cursor.getCount();
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
}
