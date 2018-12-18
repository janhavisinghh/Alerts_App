package com.example.android.exampleapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.exampleapp.databinding.NotificationItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryOneViewHolder> {
    public ArrayList<DataObjs> data;
    private Context context;
    private NotificationItemBinding binding;

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
    public void onBindViewHolder(final CategoryAdapter.CategoryOneViewHolder holder, int position) {
        holder.bind(position, data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    /**
     * @param data
     */
    public void setData(ArrayList<DataObjs> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class CategoryOneViewHolder extends RecyclerView.ViewHolder {
        /**
         * @param itemView
         */
        public CategoryOneViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * @param item
         * @param data_obj
         */
        public void bind(final int item, final DataObjs data_obj) {
            final String title = data.get(item).getTitle();
            binding.textView.setText(title);
        }
    }
}
