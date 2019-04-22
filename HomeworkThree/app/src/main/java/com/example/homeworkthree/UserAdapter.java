package com.example.homeworkthree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userId, name;
        public  MyViewHolder(View view) {
            super(view);
            userId = view.findViewById(R.id.user_id);
            name = view.findViewById(R.id.text_view_name);
        }
    }

    public UserAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mDataset.get(position);
        holder.userId.setText(user.userId);
        holder.name.setText(user.name);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
