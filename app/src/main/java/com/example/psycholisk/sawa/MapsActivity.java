package com.example.psycholisk.sawa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    public static final String REQUEST_TAG = "MainVolleyActivity";
//    private RequestQueue mQueue;
//    private final String apiDomain = "http://naderkanounji.com/sawa/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


//        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
//        String url = apiDomain + "2.5/weather?q=London,uk";

//        ImageView membershipBg = (ImageView)findViewById(R.id.membershipbg);
//        membershipBg.setVisibility(View.INVISIBLE);

        final FragmentManager _fragmentManager = getFragmentManager();

        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();

//        MapFragment mapFrag = new MapFragment();
//        _fragmentTransaction.add(R.id.mapframe ,mapFrag, "MapFragment");
//        LoginFragment loginFrag = new LoginFragment();
//        _fragmentTransaction.add(R.id.membershipframe, loginFrag, "LoginFragment");
//        _fragmentTransaction.commit();

        MapFragment mapFragment = (MapFragment) _fragmentManager
                .findFragmentByTag("MapFragment");
        mapFragment.getMapAsync(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

//
//        FragmentManager fm = getSupportFragmentManager();
//        final Fragment loginFrag = fm.findFragmentById(R.id.login_fragment);
//        final Fragment signupFrag = fm.findFragmentById(R.id.signup_fragment);
//
//        fm.beginTransaction().hide(signupFrag).commit();

//        Button signupbtn_login = (Button)findViewById(R.id.signupbtn_login);
//
//        signupbtn_login.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//                SignupFragment signupFrag = new SignupFragment();
//                _fragmentTransaction.replace(R.id.membershipframe, signupFrag).commit();
//
//            }
//        });
//
//          mapFragment.getView().setClickable(false);
    }


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

        FragmentManager _fragmentManager = getFragmentManager();
        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();

        StatusFragment statusFrag = new StatusFragment();
        _fragmentTransaction.add(R.id.statusframe, statusFrag, "StatusFragment");
        LoginFragment loginFragment = (LoginFragment)_fragmentManager.findFragmentByTag("LoginFragment");
        _fragmentTransaction.remove(loginFragment);

        ImageView membershipBG = (ImageView)findViewById(R.id.membershipbg);
        membershipBG.setVisibility(View.INVISIBLE);
    }

    public void FinalizeLogin() {
//        FragmentManager _fragmentManager = getFragmentManager();
//        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//
//        MapFragment mapFragment = (MapFragment) _fragmentManager.findFragmentByTag("MapFragment");
//        mapFragment.getMapAsync(this);
    }
}
