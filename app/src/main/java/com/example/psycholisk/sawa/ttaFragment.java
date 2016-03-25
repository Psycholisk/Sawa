package com.example.psycholisk.sawa;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class ttaFragment extends Fragment {

    public static final String REQUEST_TAG = "LoginVolleyFragment";
    private RequestQueue mQueue;
    private final String apiDomain = "http://naderkanounji.com/sawa/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tta, container, false);
        final SharedPreferences _sharedPref = getActivity().getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);

        final String userToken = _sharedPref.getString("userToken", "");
        int userStatus = _sharedPref.getInt("userStatus", 0);

        final TextView timeDisplay = (TextView)view.findViewById(R.id.time_display);
        final SeekBar timeSeekbar = (SeekBar)view.findViewById(R.id.time_seekbar);
        Button ttaDone = (Button)view.findViewById(R.id.tta_done);


        timeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += 5;
                if(progress < 60){
                    timeDisplay.setText(String.valueOf(progress) + " mins");
                }else {
                    if(progress % 60 == 0){
                        timeDisplay.setText(String.valueOf(progress / 60) + " hours");
                    }else {
                        timeDisplay.setText(String.valueOf(progress / 60) + " hours, " + String.valueOf(progress % 60) + " mins");
                    }
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ttaDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).UpdateStatus(userToken, 2, timeSeekbar.getProgress() + 5);
            }
        });
        return view;
    }



}
