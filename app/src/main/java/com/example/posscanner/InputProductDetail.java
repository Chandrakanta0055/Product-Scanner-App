package com.example.posscanner;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class InputProductDetail extends Fragment {

    private Button btnOk;
    private String url = "https://creativecollege.in/TechFest/Add_POS.php";
    Spinner spinner;
    String[] measures = {"Kg","gm","L","ml"};
    String unit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_product_detail, container, false);

        TextView barcode = view.findViewById(R.id.barcode);
        EditText brand = view.findViewById(R.id.pbrand);
        EditText pname = view.findViewById(R.id.pname);
        EditText price = view.findViewById(R.id.PriceText);
        EditText quantity = view.findViewById(R.id.quantity);
        EditText NumberofPr = view.findViewById(R.id.Number);
        btnOk = view.findViewById(R.id.btnOK);
        spinner = view.findViewById(R.id.spin);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,measures);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                unit = measures[position];
                Toast.makeText(getContext(),unit,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String s = bundle.getString("key");
            barcode.setText(s);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bcode = barcode.getText().toString();
                String Pbrand = brand.getText().toString();
                String Pname = pname.getText().toString();
                String Price = price.getText().toString();
                String Quantity = quantity.getText().toString();
                String NofProduct = NumberofPr.getText().toString();
                String Quan_Units = Quantity.concat(unit);
//                Toast.makeText(getContext(), Quan_Unit, Toast.LENGTH_SHORT).show();

                if (Bcode.isEmpty()) {
                    Toast.makeText(getContext(), "Put Barcode Number", Toast.LENGTH_SHORT).show();
                } else if (Pbrand.isEmpty()) {
                    Toast.makeText(getContext(), "Enter The Brand Name", Toast.LENGTH_SHORT).show();
                } else if (Pname.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Product Name", Toast.LENGTH_SHORT).show();
                } else if (Price.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the price", Toast.LENGTH_SHORT).show();
                } else if (Quantity.isEmpty() && unit.isEmpty() && Quan_Units.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Quantity", Toast.LENGTH_SHORT).show();
                } else if (NofProduct.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Number of Product", Toast.LENGTH_SHORT).show();
                } else {

                    // Create JSON object for the request
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("barcode_number", Bcode);
                        jsonObject.put("product_brand", Pbrand);
                        jsonObject.put("product_name", Pname);
                        jsonObject.put("price", Price);
                        jsonObject.put("product_quantity", Quan_Units);
                        jsonObject.put("num_available", NofProduct);

                        //
                        Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellerNavigationFragment()).commit();

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
                                            Toast.makeText(getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                                            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new SellerNavigationFragment()).commit();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to add data to the database", Toast.LENGTH_SHORT).show();
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
