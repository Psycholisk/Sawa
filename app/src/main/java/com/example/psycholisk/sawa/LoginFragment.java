package com.example.psycholisk.sawa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    public static final String REQUEST_TAG = "LoginVolleyFragment";
    private RequestQueue mQueue;
    private final String apiDomain = "http://naderkanounji.com/sawa/";
    public utils utils = new utils();
    public TextView valMessage;
    private static String userToken = "";
    public GoogleMap gMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view  = inflater.inflate(R.layout.fragment_login, container, false);


        final FragmentManager _fragmentManager = getFragmentManager();
     //   Button signupButton = (Button)view.findViewById(R.id.signupbtn_login);
        TextView goToSignup = (TextView)view.findViewById(R.id.gotosignup);
        Button signinbtn = (Button)view.findViewById(R.id.loginsubmit);
        valMessage = (TextView)view.findViewById(R.id.validationmessage);





        signinbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText emailinput = (EditText)view.findViewById(R.id.loginemail);
                EditText passwordinput = (EditText)view.findViewById(R.id.loginpassword);

                if(utils.isEmailValid(emailinput.getText().toString())) {
                    mQueue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                    String url = apiDomain + "membership.php?method=Login&email=" + emailinput.getText() + "&password=" + passwordinput.getText();
                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url,  new JSONObject(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if(Integer.parseInt(response.getString("errorcode")) == 0)
                                {
                                    userToken = response.getString("message");

                                    //Launch map + status fragments

                                    ImageView membershipBG = (ImageView)getActivity().findViewById(R.id.membershipbg);
                                    membershipBG.setVisibility(View.INVISIBLE);
//                                    FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
                                    valMessage.setText("");
                                    ((MapsActivity)getActivity()).FinalizeLogin();
//                                    StatusFragment statusFrag = new StatusFragment();
//                                    _fragmentTransaction.add(R.id.mapframe, mapFrag, "MapFragment");
//                                    _fragmentTransaction.add(R.id.statusframe, statusFrag, "StatusFragment");
//                                    LoginFragment loginFragment = (LoginFragment)_fragmentManager.findFragmentByTag("LoginFragment");
//                                    _fragmentTransaction.remove(loginFragment);
                           //         _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();
//                                    SignupFragment signupFrag = new SignupFragment();
//                                    _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();
                                }else {
                                    valMessage.setText(response.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                valMessage.setText(R.string.unknown_error);
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    valMessage.setText(R.string.unknown_error);
//                                    FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
//                                    SignupFragment signupFrag = new SignupFragment();
//                                    _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();
                                }
                            });
                    jsonRequest.setTag(REQUEST_TAG);
                    mQueue.add(jsonRequest);
                }else {
                    valMessage.setText(R.string.email_format);
                }

            }
        });
        goToSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();
                SignupFragment signupFrag = new SignupFragment();
                _fragmentTransaction.replace(R.id.membershipframe, signupFrag).addToBackStack(null).commit();
            }
        });
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }
}
