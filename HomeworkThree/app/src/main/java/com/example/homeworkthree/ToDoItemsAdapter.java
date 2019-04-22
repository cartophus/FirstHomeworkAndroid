package com.example.homeworkthree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ToDoItemsAdapter extends RecyclerView.Adapter<ToDoItemsAdapter.MyViewHolder> {
    private List<ToDoItem> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public  MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_view_title);
        }
    }

    public ToDoItemsAdapter(List<ToDoItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ToDoItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.todo_item_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ToDoItem toDoItem = mDataset.get(position);
        holder.title.setText(toDoItem.title);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
