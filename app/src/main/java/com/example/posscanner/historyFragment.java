package com.example.posscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class historyFragment extends Fragment {
    private ArrayList<HistoryDetail> historyDetails = new ArrayList<>();
    private historyAdapter adapter;

    private TextView clearAll;
    String date,Time;

    public historyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ListView listView = view.findViewById(R.id.historylistview);
        clearAll = view.findViewById(R.id.clearAll);

            loadDataAndUpdateList("date", "Time");




        // Set up the adapter
        adapter = new historyAdapter(getContext(), historyDetails);
        listView.setAdapter(adapter);

        // Set onClickListener for the clearAll TextView
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllData();
            }
        });

        return view;
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
//        historyDetails.add(new HistoryDetail(date, shoppingPrice));

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

    private void clearAllData() {
        // Clear both the list and SharedPreferences data
        historyDetails.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("historyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Remove all entries from the SharedPreferences
        editor.apply();
    }
}

