package com.example.tema1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class A2F1 extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a2_f1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button myBtn = view.findViewById(R.id.a2f1btn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment fragment = new A2F2();
                FragmentManager fm = getFragmentManager();
                try
                {
                    FragmentTransaction ft = fm.beginTransaction();
                    String tag = fragment.getClass().getSimpleName();
                    ft.replace(R.id.fragment_holder, fragment, tag);
                    ft.commit();
                }
                catch(Exception e){}
            }
        });
    }
}
