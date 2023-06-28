package com.example.lab04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetailsFragment extends Fragment {

    DetailsFragment fragment = new DetailsFragment();
    Bundle b = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle b = getArguments();

        if (b != null){
            String value = b.getString("");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

}