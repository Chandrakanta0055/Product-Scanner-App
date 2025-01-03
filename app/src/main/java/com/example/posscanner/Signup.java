package com.example.posscanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends Fragment {

    String url = "https://creativecollege.in/TechFest/Signup.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        EditText username = view.findViewById(R.id.username);
        EditText email = view.findViewById(R.id.email);
        EditText phone = view.findViewById(R.id.phone);
        EditText Password = view.findViewById(R.id.password);
        Button Signup = view.findViewById(R.id.signupbtn);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String U_Name = username.getText().toString();
                String E_Mail = email.getText().toString();
                String P_Hone = phone.getText().toString();
                String P_Assword = Password.getText().toString();

                if(U_Name.isEmpty()){
                    Toast.makeText(getContext(), "Enter the User Name", Toast.LENGTH_SHORT).show();
                } else if (E_Mail.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Email ID", Toast.LENGTH_SHORT).show();
                } else if (P_Hone.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Phone Number", Toast.LENGTH_SHORT).show();
                } else if (P_Assword.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Password", Toast.LENGTH_SHORT).show();
                }else {
                    // Create JSON object for the request
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("User", U_Name);
                        jsonObject.put("Email", E_Mail);
                        jsonObject.put("Phone", P_Hone);
                        jsonObject.put("Pass", P_Assword);

                        //
                        Toast.makeText(getContext(), "Signup Sucessful", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginSignup()).commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Make the POST request using Volley
                    RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("status").equalsIgnoreCase("success")) {
                                            Toast.makeText(getContext(), "Signup Sucessful", Toast.LENGTH_SHORT).show();
                                            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginSignup()).commit();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to Signup", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Add the request to the RequestQueue
                    requestQueue.add(request);
                }
            }
        });


        return view;
    }
}