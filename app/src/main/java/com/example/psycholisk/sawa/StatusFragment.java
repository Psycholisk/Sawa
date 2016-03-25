package com.example.psycholisk.sawa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class StatusFragment extends Fragment {
    public static final String REQUEST_TAG = "LoginVolleyFragment";
    private RequestQueue mQueue;
    private final String apiDomain = "http://naderkanounji.com/sawa/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_status, container, false);
        final SharedPreferences _sharedPref = getActivity().getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);

        final String userToken = _sharedPref.getString("userToken", "");
        int userStatus = _sharedPref.getInt("userStatus", 0);

        Button availablebtn = (Button)view.findViewById(R.id.available_btn);
        Button busybtn = (Button)view.findViewById(R.id.busy_btn);



        //Button clicks
        availablebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).UpdateStatus(userToken, 1, 0);
            }
        });

        busybtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager _fragmentManager = getFragmentManager();
                FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();

                ttaFragment ttaFrag = new ttaFragment();
                _fragmentTransaction.replace(R.id.statuscontainer, ttaFrag, "TTAFragment").addToBackStack(null).commit();

            }
        });
        return view;
    }



}
