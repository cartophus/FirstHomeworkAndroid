package com.example.homeworkone;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class F1A1 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> mDataset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f1a1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());

        Button insert = view.findViewById(R.id.insert);
        insert.setOnClickListener(v -> {
            EditText firstName = view.findViewById(R.id.first_name);
            String firstNameValue = String.valueOf(firstName.getText());

            EditText lastName = view.findViewById(R.id.last_name);
            String lastNameValue = String.valueOf(lastName.getText());

            UserRepository userRepository = new UserRepository(getContext());
            userRepository.insertTask(new User(firstNameValue, lastNameValue), new OnUserRepositoryActionListener() {
                @Override
                public void actionSuccess() {
                    mDataset.add(new User(firstNameValue, lastNameValue));
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void actionSuccess(List<User> result) {
                    //
                }

                @Override
                public void actionFailed() {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button remove = view.findViewById(R.id.remove);
        remove.setOnClickListener(v -> {
            EditText firstName = view.findViewById(R.id.first_name);
            String firstNameValue = String.valueOf(firstName.getText());

            EditText lastName = view.findViewById(R.id.last_name);
            String lastNameValue = String.valueOf(lastName.getText());

            UserRepository userRepository = new UserRepository(getContext());
            userRepository.findTask(firstNameValue, lastNameValue, new OnUserRepositoryActionListener() {
                @Override
                public void actionSuccess() {
                    int index = CustomIndexOf(mDataset, firstNameValue, lastNameValue);
                    if (index != -1) {
                        mDataset.remove(index);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "gucci", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "not founds", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void actionSuccess(List<User> result) {
                    //
                }

                @Override
                public void actionFailed() {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        UserRepository userRepository = new UserRepository(getContext());
        userRepository.getAllUsers(new OnUserRepositoryActionListener() {
            @Override
            public void actionSuccess() {
                //
            }

            @Override
            public void actionSuccess(List<User> result) {
                mDataset = result;
                mAdapter = new MyAdapter(result);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void actionFailed() {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int CustomIndexOf(List<User> users, String firstName, String lastName) {
        for (int it = 0; it < users.size(); it++) {
            User user = users.get(it);
            if (user.firstName.equals(firstName) && user.lastName.equals(lastName)) {
                return it;
            }
        }

        return -1;
    }

    public void getToDoItems(){
        String url = "https://jsonplaceholder.typicode.com/todos?userId=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response){
                List<ToDoItem> toDoItemList = new ArrayList<>();

                for(int index = 0; index < response.length(); index++) {
                    try {
                        ToDoItem item = new ToDoItem(response.getJSONObject((index)));
                        toDoItemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getContext(), "Volley error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
