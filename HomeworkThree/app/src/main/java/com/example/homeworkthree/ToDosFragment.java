package com.example.homeworkthree;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ToDosFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ToDoItem> mDataset;
    private Bundle arguments;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todos_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arguments = getArguments();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Getting to do list ...");
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        fuckMyLifeAgain(view);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        layoutManager = new LinearLayoutManager(getContext());
        setUpRecyclerView();
    }

    public void fuckMyLifeAgain(View v) {
        TextView title = v.findViewById(R.id.text_view_title);
        String titleValue = title.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("title", titleValue);
        AlarmFragment alarmFragment = new AlarmFragment();
        alarmFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, alarmFragment, "alarmFragment");
        fragmentTransaction.addToBackStack("alarmFragment");
        fragmentTransaction.commit();
    }

    public void getToDoItems(){
        String url = "https://jsonplaceholder.typicode.com/todos?userId=" + arguments.get("userId");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            progressDialog.cancel();
            for(int index = 0; index < response.length(); index++) {
                try {
                    ToDoItem item = new ToDoItem(response.getJSONObject((index)));
                    mDataset.add(item);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            progressDialog.cancel();
            Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            FragmentManager fragmentManager = getFragmentManager();
            Fragment toDosFragment = fragmentManager.findFragmentByTag("toDosFragment");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(toDosFragment);
            fragmentTransaction.commit();
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void setUpRecyclerView() {
        mDataset = new ArrayList<>();
        progressDialog.show();
        getToDoItems();
        mAdapter = new ToDoItemsAdapter(mDataset);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
