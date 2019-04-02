package com.example.tema1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_main);

        Fragment fragment = new A2F1();
        FragmentManager fm = getSupportFragmentManager();
        try
        {
            FragmentTransaction ft = fm.beginTransaction();
            String tag = fragment.getClass().getSimpleName();
            ft.replace(R.id.fragment_holder_2, fragment, tag);
            ft.commit();
        }
        catch(Exception e){}
    }
}
