package com.example.posscanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
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

public class LoginSignup extends Fragment {

    Button btn1, btn2;
    EditText editText1, editText2;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);

        editText1 = view.findViewById(R.id.email);
        editText2 = view.findViewById(R.id.pass);

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                claaScanner();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup signup = new Signup();
                FragmentTransaction transaction = requireFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, signup);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    public void claaScanner() {
        String user = editText1.getText().toString();
        String password = editText2.getText().toString();

        // Create JSON object for the request
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Phone", user);
            jsonObject.put("Pass", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make the POST request using Volley
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://creativecollege.in/TechFest/Login.php", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("message").equalsIgnoreCase("Success")) {
                                // Save user data in SharedPreferences after successful login
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user);
                                editor.putString("loginn","userLogin");
                                editor.apply();
                                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                                // Navigate to the desired fragment after successful login
                                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new NavigationBar()).commit();

                            } else if (response.getString("message").equalsIgnoreCase("Seller")) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user);
                                editor.putString("loginn","sellerLogin");
                                editor.apply();
                                Toast.makeText(getContext(), "Seller Successful", Toast.LENGTH_LONG).show();

                                getParentFragmentManager()
                                        .beginTransaction().replace(R.id.fragment_container, new SellerNavigationFragment()).commit();
                                // Handle Seller Success if needed
                            } else {
                                Toast.makeText(getContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response if needed
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }
}
