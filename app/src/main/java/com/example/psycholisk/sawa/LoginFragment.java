package com.example.psycholisk.sawa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_login, container, false);

        Button signupButton = (Button)view.findViewById(R.id.signupbtn_login);
        final FragmentManager _fragmentManager = getFragmentManager();

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
                SignupFragment signupFrag = new SignupFragment();
                _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();
            }
        });
        return view;
    }
}
