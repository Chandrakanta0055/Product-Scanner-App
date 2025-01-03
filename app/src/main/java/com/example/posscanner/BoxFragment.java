package com.example.posscanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoxFragment extends Fragment {

    ListView listView;

    public BoxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_box, container, false);

        String url = "https://creativecollege.in/TechFest/retrieve.php";

        ArrayList<String> nameArraylist = new ArrayList<>();

        // Create sample user data
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String barcodeNumber = jsonObject.optString("barcode_number");
                        String productbrand = jsonObject.optString("product_brand");
                        String productname = jsonObject.optString("product_name");
                        String price = jsonObject.optString("price");
                        String productquantity = jsonObject.optString("product_quantity");
                        String numavailable = jsonObject.optString("num_available");

                        productDetailList.add(new ProductDetail(barcodeNumber,productbrand, productname, productquantity, price,numavailable));

                        // Create the custom adapter
                        SellerListAdapter adapter = new SellerListAdapter(getActivity(), productDetailList);

                        // Get a reference to the ListView
                        listView = view.findViewById(R.id.seller_listview);

                        // Set the adapter for the ListView
                        listView.setAdapter(adapter);

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        return view;
    }
}