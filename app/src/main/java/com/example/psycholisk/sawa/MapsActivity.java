package com.example.psycholisk.sawa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String REQUEST_TAG = "MapVolleyFragment";
    private RequestQueue mQueue;
    private final String apiDomain = "http://naderkanounji.com/sawa/";
    private TextView vanish_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final FragmentManager _fragmentManager = getFragmentManager();
        final FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
        final SharedPreferences _sharedPref = getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);
//
//
//        String userToken = _sharedPref.getString("userToken", "");
        int userStatus = _sharedPref.getInt("userStatus", 0);
//
        vanish_map = (TextView)findViewById(R.id.vanish_map);
//
//        LoginFragment loginFrag = new LoginFragment();
//        _fragmentTransaction.add(R.id.membershipframe, loginFrag, "LoginFragment");
//        _fragmentTransaction.commit();



//        if(userToken.isEmpty()) {
//            LoginFragment loginFrag = new LoginFragment();
//            _fragmentTransaction.add(R.id.membershipframe, loginFrag, "LoginFragment");
//            _fragmentTransaction.commit();
//
//        }else {
//            SignupFragment signupFrag = new SignupFragment();
//            _fragmentTransaction.add(R.id.membershipframe, signupFrag, "LoginFragment");
//            _fragmentTransaction.commit();
//
//            SharedPreferences.Editor editor = _sharedPref.edit();
//            editor.putString("userToken", "");
//        }


        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(userStatus == 0) {
            StatusFragment statusFrag = new StatusFragment();
            _fragmentTransaction.add(R.id.statuscontainer, statusFrag, "StatusFragment");
            _fragmentTransaction.commit();

            mapFragment.getView().setClickable(false);
            vanish_map.setClickable(false);
        }

        vanish_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusFragment statusFrag = new StatusFragment();
                _fragmentTransaction.add(R.id.statuscontainer, statusFrag, "StatusFragment");
                _fragmentTransaction.commit();

                mapFragment.getView().setClickable(false);
                vanish_map.setClickable(false);
                SharedPreferences.Editor editor = _sharedPref.edit();
                editor.putInt("userStatus", 0).commit();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

//
//          mapFragment.getView().setClickable(false);

    }
//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() > 0 ){
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the sawa.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        FragmentManager _fragmentManager = getFragmentManager();
//        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//
//        StatusFragment statusFrag = new StatusFragment();
//        _fragmentTransaction.add(R.id.statusframe, statusFrag, "StatusFragment");
//        LoginFragment loginFragment = (LoginFragment)_fragmentManager.findFragmentByTag("LoginFragment");
//        _fragmentTransaction.remove(loginFragment);

//        ImageView membershipBG = (ImageView)findViewById(R.id.membershipbg);
//        membershipBG.setVisibility(View.INVISIBLE);
    }

    public void OpenTTA() {
//        FragmentManager _fragmentManager = getFragmentManager();
//        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//
//        MapFragment mapFragment = (MapFragment) _fragmentManager.findFragmentByTag("MapFragment");
//        mapFragment.getMapAsync(this);


    }
    public void UpdateStatus(String userToken, final int statusId, int tta){
        mQueue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = apiDomain + "membership.php?method=UpdateStatus&token=" + userToken + "&statusId=" + statusId;
        if(tta != 0){
            url += "&tta=" + tta;
        }
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (Integer.parseInt(response.getString("errorcode")) == 0) {
                        //success
//                        SharedPreferences _sharedPref = getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE).edit();
                        editor.putInt("userStatus", statusId).commit();
                        FragmentManager _fragmentManager = getFragmentManager();
                        ttaFragment ttaFrag = (ttaFragment)_fragmentManager.findFragmentByTag("TTAFragment");
                        _fragmentManager.beginTransaction().remove(ttaFrag).commit();
                        getSupportFragmentManager().findFragmentById(R.id.map).getView().setClickable(true);
                        vanish_map.setClickable(true);
                    } else {
                        //display error
                    }
                } catch (JSONException e) {
                    //dispolay error
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //dispolay error
                    }
                });
        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);
    }



}
