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
    public TextView emailVal;
    public TextView passwordVal;
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
        emailVal = (TextView)view.findViewById(R.id.login_emailvaldiation);
        passwordVal = (TextView)view.findViewById(R.id.login_passwordvaldiation);



        signinbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Reset validation messages
                emailVal.setText("");
                passwordVal.setText("");

                boolean isFormValid = true;
//                EditText emailinput = (EditText)view.findViewById(R.id.loginemail);
//                EditText passwordinput = (EditText)view.findViewById(R.id.loginpassword);
                String emailText = ((EditText)view.findViewById(R.id.loginemail)).getText().toString();
                String passwordText = ((EditText) view.findViewById(R.id.loginpassword)).getText().toString();

                //Validating inputs
                if(emailText == null || emailText.isEmpty()) {
                    isFormValid = false;
                    emailVal.setText(R.string.required_field);
                }else {
                    if(!utils.isEmailValid(emailText)) {
                        isFormValid =false;
                        emailVal.setText(R.string.email_format);
                    }
                }
                if(passwordText == null || passwordText.isEmpty()) {
                    isFormValid = false;
                    passwordVal.setText(R.string.required_field);
                }else {
                    if(passwordText.length() < 8){
                        isFormValid = false;
                        passwordVal.setText(R.string.password_length);
                    }
                }

                if(isFormValid) {
                    mQueue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                    String url = apiDomain + "membership.php?method=Login&email=" + emailText + "&password=" + passwordText;
                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url,  new JSONObject(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        try {
                            if(Integer.parseInt(response.getString("errorcode")) == 0)
                            {
                                //save token
                                userToken = response.getString("message");

                                //Launch map + status fragments
                                passwordVal.setText(userToken);
                                ((MapsActivity)getActivity()).FinalizeLogin();
                            }else {
                                passwordVal.setText(response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            passwordVal.setText(R.string.unknown_error);
                        }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            passwordVal.setText(R.string.unknown_error);
                        }
                    });
                    jsonRequest.setTag(REQUEST_TAG);
                    mQueue.add(jsonRequest);
                }
            }
        });
        goToSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).GoToSignUp();
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
