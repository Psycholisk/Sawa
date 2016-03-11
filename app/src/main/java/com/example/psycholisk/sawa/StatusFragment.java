package com.example.psycholisk.sawa;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class StatusFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_status, container, false);

        RelativeLayout parentView = (RelativeLayout) view.findViewById(R.id.statusoverlay);

        parentView.setOnClickListener(null);

        return view;
    }

}
