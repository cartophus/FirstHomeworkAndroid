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

public class UserFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> mDataset;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Getting users ...");
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        fuckMyLife(view);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        layoutManager = new LinearLayoutManager(getContext());
        setUpRecyclerView();
    }

    public void fuckMyLife(View v) {
        TextView userId = v.findViewById(R.id.user_id);
        String userIdValue = userId.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("userId", userIdValue);
        ToDosFragment toDosFragment = new ToDosFragment();
        toDosFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, toDosFragment, "toDosFragment");
        fragmentTransaction.addToBackStack("toDosFragment");
        fragmentTransaction.commit();
    }

    public void getUsers(){
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            progressDialog.cancel();
            for(int index = 0; index < response.length(); index++) {
                try {
                    User user = new User(response.getJSONObject((index)));
                    mDataset.add(user);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            progressDialog.cancel();
            Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void setUpRecyclerView() {
        mDataset = new ArrayList<>();
        progressDialog.show();
        getUsers();
        mAdapter = new UserAdapter(mDataset);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
