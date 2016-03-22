package com.example.psycholisk.sawa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager _fragmentManager = getFragmentManager();
        final FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
        final SharedPreferences _sharedPref = getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);


        String userToken = _sharedPref.getString("userToken", "");



//        LoginFragment loginFrag = new LoginFragment();
//        _fragmentTransaction.add(R.id.membershipframe, loginFrag, "LoginFragment");
//        _fragmentTransaction.commit();

        if(userToken.isEmpty()) {
            LoginFragment loginFrag = new LoginFragment();
            _fragmentTransaction.add(R.id.membershipframe, loginFrag, "LoginFragment");
            _fragmentTransaction.commit();

            findViewById(R.id.launchingloader).setVisibility(View.INVISIBLE);

        }else {
            SignupFragment signupFrag = new SignupFragment();
            _fragmentTransaction.add(R.id.membershipframe, signupFrag, "LoginFragment");
            _fragmentTransaction.commit();

//            SharedPreferences.Editor editor = _sharedPref.edit();
//            editor.putString("userToken", "");
//            editor.commit();

            LaunchMap("");
//
        }


//        MapFragment mapFragment = (MapFragment) _fragmentManager
//                .findFragmentByTag("MapFragment");
//        mapFragment.getMapAsync(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

//
//        FragmentManager fm = getSupportFragmentManager();
//        final Fragment loginFrag = fm.findFragmentById(R.id.login_fragment);
//        final Fragment signupFrag = fm.findFragmentById(R.id.signup_fragment);
//
//          mapFragment.getView().setClickable(false);

    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void LaunchMap(String userToken) {
        if(!userToken.isEmpty()) {
            SharedPreferences preferences = this.getApplicationContext().getSharedPreferences(getString(R.string.user_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("userToken", userToken);
            edit.commit();
        }
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
//        FragmentManager _fragmentManager = getFragmentManager();
//        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//
//        MapFragment mapFragment = (MapFragment) _fragmentManager.findFragmentByTag("MapFragment");
//        mapFragment.getMapAsync(this);
    }
    public void GoToSignUp() {
        FragmentManager _fragmentManager = getFragmentManager();
        FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
        SignupFragment signupFrag = new SignupFragment();
        _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();


    }

}
