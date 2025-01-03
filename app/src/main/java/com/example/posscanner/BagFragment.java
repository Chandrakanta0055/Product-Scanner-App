package com.example.posscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BagFragment extends Fragment {

    TextView total;
    float totalPrice = 0f;
    Button pay;

    public BagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        if (view != null) {
            total = view.findViewById(R.id.Total);

            ArrayList<Products> productDetailList = new ArrayList<>();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BagData", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("BagItem", null);

            if (json != null) {
                Type type = new TypeToken<ArrayList<Products>>() {}.getType();
                productDetailList = gson.fromJson(json, type);
            }

            totalPrice = calculateTotalPrice(productDetailList);
            total.setText("Rs. " + totalPrice);

            ListAdapter adapter = new ListAdapter(getContext(), productDetailList != null ? productDetailList : new ArrayList<>());

            ListView listView = view.findViewById(R.id.listview);
            listView.setAdapter(adapter);
        } else {
            // Handle the case where the view is null
        }

        pay = view.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalPrice > 0.0) {
                    String totalAmount = String.valueOf(totalPrice);

                    Bundle bundle = new Bundle();
                    bundle.putString("key", totalAmount);

                    payment_option fragment = new payment_option();
                    fragment.setArguments(bundle);

                    // Use the existing 'fragment' instance instead of creating a new one
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    Toast.makeText(getContext(), "Select your payment Option", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Your cart is empty. Add some items to pay.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private float calculateTotalPrice(ArrayList<Products> productDetailList) {
        float totalPrice = 0.0f;
        for (Products product : productDetailList) {
            int quantity = product.NoOfProduct;
            float price = Float.parseFloat(product.getPrice());
            totalPrice += quantity * price;
        }
        return totalPrice;
    }
}
