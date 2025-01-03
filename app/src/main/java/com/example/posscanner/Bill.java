package com.example.posscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.Manifest;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Bill extends Fragment {
    private Button download;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private ArrayList<HistoryDetail> historyDetails = new ArrayList<>();
    private ArrayList<Products> product_DetailList = new ArrayList<>();
    Bill_adapter adapter;
    String value;
    private View bill;  // Declare bill as a class-level variable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bill = inflater.inflate(R.layout.fragment_bill, container, false);



        SharedPreferences sharedPreferences = getContext().getSharedPreferences("BagData", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("BagItem", null);
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Products>>() {
        }.getType();
        ArrayList<Products> arrayList = gson.fromJson(json, type);

        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                String Pname = arrayList.get(i).getP_name();
                String price = arrayList.get(i).getPrice();
                int Quantity = arrayList.get(i).NoOfProduct;
                product_DetailList.add(new Products(Pname, Quantity, price));
            }
        }

        adapter = new Bill_adapter(product_DetailList);

        RecyclerView recyclerView = bill.findViewById(R.id.bill_list);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            value = bundle.getString("keys");
            TextView TpriCe = bill.findViewById(R.id.tpri_ce);
            TpriCe.setText(value);
            // Do something with the value
        } else {
            Toast.makeText(getContext(), "data not pass", Toast.LENGTH_SHORT).show();
        }


        //clear all data
        //Payment Sucessfully
        Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();

        download = bill.findViewById(R.id.downloadBtn);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearAllData();
//            bpass("29/12/2023",value);
                loadDataAndUpdateList("29/12/2023", value);

                // Capture the content of the fragment as a bitmap
                Bitmap bitmap = getBitmapFromView(bill);

                // Save the bitmap as a PNG file
                saveBitmapAsFile(bitmap);

            }
        });

        return bill;
    }


    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveBitmapAsFile(Bitmap bitmap) {
        String fileName = "bill_image.png";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(directory, fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Notify the gallery about the new file so it can be scanned and made available in the gallery app
            scanFile(file.getAbsolutePath());

            Toast.makeText(getContext(), "Bill saved successfully", Toast.LENGTH_SHORT).show();
//
//            clearAllData();
////            bpass("29/12/2023",value);
//            loadDataAndUpdateList("29/12/2023", value);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving Bill", Toast.LENGTH_SHORT).show();
        }
    }

    private void scanFile(String path) {
        MediaScannerConnection.scanFile(
                getContext(),
                new String[]{path},
                null,
                (path1, uri) -> {
                    // MediaScannerConnection callback
                }
        );


    }

    public void bpass(String D,String T){
        Bundle bundle = new Bundle();
        bundle.putString("Date", D);
        bundle.putString("time", T);


        NavigationBar fragments = new NavigationBar();
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

