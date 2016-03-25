package com.example.psycholisk.sawa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class SignupFragment extends Fragment {

    public static final String REQUEST_TAG = "SignupVolleyFragment";
    private RequestQueue mQueue;
    private final String apiDomain = "http://naderkanounji.com/sawa/";
    public utils utils = new utils();
    public TextView emailVal;
    public TextView mobileVal;
    public TextView passwordVal;
    public TextView confirmVal;
    public TextView generalVal;
    private static String userToken = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        final FragmentManager _fragmentManager = getFragmentManager();
        final FragmentTransaction _fragmentTransaction = _fragmentManager.beginTransaction();

        emailVal = (TextView)view.findViewById(R.id.register_emailvaldiation);
        mobileVal = (TextView)view.findViewById(R.id.register_mobilevaldiation);
        passwordVal = (TextView)view.findViewById(R.id.register_passwordvaldiation);
        confirmVal = (TextView)view.findViewById(R.id.register_confirmvaldiation);
        generalVal = (TextView)view.findViewById(R.id.register_generalvalidation);

        TextView navBack = (TextView)view.findViewById(R.id.nav_back);

        Button registerbtn = (Button)view.findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Reset validation messages
                emailVal.setText("");
                mobileVal.setText("");
                passwordVal.setText("");
                confirmVal.setText("");
                generalVal.setText("");

                boolean isFormValid = true;
                String emailText = ((EditText) view.findViewById(R.id.registeremail)).getText().toString();
                String mobileText = ((EditText) view.findViewById(R.id.registermobile)).getText().toString();
                String passwordText = ((EditText) view.findViewById(R.id.registerpassword)).getText().toString();
                String confirmText = ((EditText) view.findViewById(R.id.registerconfirmpassword)).getText().toString();

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
                if(mobileText == null || mobileText.isEmpty()) {
                    isFormValid = false;
                    mobileVal.setText(R.string.required_field);
                }else {
                    if(!utils.isNumeric(mobileText)) {
                        isFormValid =false;
                        mobileVal.setText(R.string.numeric_field);
                    }
                }
                if(passwordText == null || passwordText.isEmpty()) {
                    isFormValid = false;
                    passwordVal.setText(R.string.required_field);
                }else {
                    if(passwordText.length() < 8){
                        isFormValid = false;
                        passwordVal.setText(R.string.password_length);
                    }else {
                        if(!passwordText.equals(confirmText)) {
                            isFormValid = false;
                            confirmVal.setText(R.string.confirm_no_match);
                        }
                    }
                }


                if (isFormValid) {
                    mQueue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                    String url = apiDomain + "membership.php?method=Register&email=" + emailText + "&mobile=" + mobileText + "&password=" + passwordText;
                    final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if (Integer.parseInt(response.getString("errorcode")) == 0) {
                                    userToken = response.getString("message");

                                    passwordVal.setText("");
                                    ((MainActivity)getActivity()).LaunchMap(userToken);
                                } else {
                                    generalVal.setText(response.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                generalVal.setText(R.string.unknown_error);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            generalVal.setText(R.string.unknown_error);
                        }
                    });
                    jsonRequest.setTag(REQUEST_TAG);
                    mQueue.add(jsonRequest);
                }
            }
        });


        navBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0 ){
                    getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }


}
