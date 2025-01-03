package com.example.posscanner;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScannerFragment extends Fragment {


    private CompoundBarcodeView barcodeView;
    private TextView scannedBarcodeTextView;
    static String barcodeNumber;

    ArrayList<Products> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);


        // Initialize UI elements
        barcodeView = view.findViewById(R.id.barcodeScannerView);
        scannedBarcodeTextView = view.findViewById(R.id.scannedBarcodeTextView);

        // Set up barcode scanner callback
        barcodeView.decodeSingle(result -> {
            if (result.getText() != null) {
                barcodeNumber = result.getText().toString();
                // Update the TextView with the scanned barcode number
                scannedBarcodeTextView.setText(barcodeNumber);

                Retrive(barcodeNumber);

                // show the dialog Details
//                showDetailDialog("102985", "brand1", "pname", "1kg", "100");
            }
        });

        return view;
    }

    private void Retrive(String BNumbers) {
        String url = "https://creativecollege.in/TechFest/Product.php?Barcode="+BNumbers;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()) {
//                            progressBar.setVisibility(View.VISIBLE);

                        } else {
//                            progressBar.setVisibility(View.INVISIBLE);


                            String[] parts = response.split(",");
                            String Bnum = parts[0];
                            String PBrabd = parts[1];
                            String PName = parts[2];
                            String Price = parts[3];
                            String PQuan = parts[4];
                            String No_Ava = parts[5];
//                            String addressvalue = parts[6];
//                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            showDetailDialog(Bnum, PBrabd, PName, PQuan, Price);


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "CONNECTION FAILED", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showDetailDialog(String code, String brandName, String productName, String quantity, String price) {
        if (barcodeNumber.equals(code)) {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.show_detail);

            TextView BrandText = dialog.findViewById(R.id.BrandText);
            TextView ProductNameText = dialog.findViewById(R.id.ProductNameText);
            TextView PriceText = dialog.findViewById(R.id.PriceText);
            TextView QuantityText = dialog.findViewById(R.id.QuantityText);
            TextView CodeNumber = dialog.findViewById(R.id.CodeNumber);

            Button Ok = dialog.findViewById(R.id.btnOK);

            CodeNumber.setText(code);
            BrandText.setText(brandName);
            ProductNameText.setText(productName);
            PriceText.setText("Rs. " + price);
            QuantityText.setText(quantity);

            // You can set up the dialog actions here
            Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BagData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("BagItem", null);
                    Gson gson = new Gson();

                    // Check if there's data in SharedPreferences
                    if (json != null) {
                        Type type = new TypeToken<ArrayList<Products>>() {
                        }.getType();
                        ArrayList<Products> arrayList = gson.fromJson(json, type);

                        //update number of quantity
                        arrayList=UpdateProduct(arrayList,code,brandName,productName,quantity,price);

                        String njson=gson.toJson(arrayList);
                        editor.putString("BagItem",njson);
                        editor.apply();

                    }
                    else {

                        arrayList.add(new Products(code,brandName, productName, quantity, price));

                        String njson = gson.toJson(arrayList);
                        editor.putString("BagItem", njson);
                        editor.apply();
                    }
//                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new BagFragment()).commit();

                    dialog.dismiss();
                }
            });

            // Show the dialog
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume the barcode scanner view when the activity is resumed
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause the barcode scanner view when the activity is paused to release the camera resources
        barcodeView.pause();
    }

    public ArrayList<Products> UpdateProduct(ArrayList<Products> arrayList,String code, String brandName, String productName, String quantity, String price) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (barcodeNumber.equals(arrayList.get(i).getBarCode())) {
                arrayList.get(i).NoOfProduct += 1;
                return arrayList;  // Return the updated list after updating the quantity
            }
        }

        // If the barcode is not found, add the new product
        arrayList.add(new Products(code, brandName, productName, quantity, price));
        return arrayList;  // Return the updated list with the new product
    }
}
