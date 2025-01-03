package com.example.posscanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class payment_option extends Fragment {

    ImageView card,gPAY,phonePe,Amazon,cash,back;
    String value;
    ProgressDialog progressDialog;

    private ArrayList<HistoryDetail> historyDetails = new ArrayList<>();
    private ArrayList<Products> product_DetailList = new ArrayList<>();
    Bill_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Payment = inflater.inflate(R.layout.fragment_payment_option, container, false);

        //dialog
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Payment");
        progressDialog.setMessage("Payment Process");


        card = Payment.findViewById(R.id.debit);
        gPAY = Payment.findViewById(R.id.gpay);
        phonePe = Payment.findViewById(R.id.ppay);
        Amazon = Payment.findViewById(R.id.apay);
        cash = Payment.findViewById(R.id.cpay);
        back = Payment.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new BagFragment()).commit();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
             value = bundle.getString("key");
            // Do something with the value
        } else {
            Toast.makeText(getContext(), "data not pass", Toast.LENGTH_SHORT).show();
        }

       card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               bpass(value);

               delay();
           }
       });

        gPAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bpass(value);
                delay();
            }
        });

        phonePe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bpass(value);
                delay();
            }
        });

        Amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delay();
//                bpass(value);
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bpass(value);
                delay();
            }
        });


        return Payment;
    }


    public  void delay()
    {
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                //clear all the data from listview
//                clearAllData();
//                loadDataAndUpdateList("12|12|2023",value);
                bpass(value);

            }
        },3000);
    }

    public void bpass(String t){
        Bundle bundle = new Bundle();
        bundle.putString("keys", t);

        Bill fragments = new Bill();
        fragments.setArguments(bundle);

        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments).commit();
    }


    private void clearAllData() {
        // Clear both the list and SharedPreferences data
        product_DetailList.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BagData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Remove all entries from the SharedPreferences
        editor.apply();
    }

    private void loadDataAndUpdateList(String date, String shoppingPrice) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("historyData", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("historyData", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<HistoryDetail>>() {}.getType();
            historyDetails = gson.fromJson(json, type);
        }

        // Add new data or perform any other updates as needed
        historyDetails.add(new HistoryDetail(date, shoppingPrice));
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new NavigationBar()).commit();

        // Notify the adapter that the data has changed
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        // Save the updated data to SharedPreferences
        storeSharedPreferences();
    }
    private void storeSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("historyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(historyDetails);
        editor.putString("historyData", json);
        editor.apply();
    }



}

