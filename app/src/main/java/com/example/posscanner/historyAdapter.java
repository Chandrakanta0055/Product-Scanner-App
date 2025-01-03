
// ListAdapter.java
package com.example.posscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class historyAdapter extends ArrayAdapter<HistoryDetail> {
    private ArrayList<HistoryDetail> historyDetailArrayList;

    public historyAdapter(Context context, ArrayList<HistoryDetail> historyDetailArrayList) {
        super(context, R.layout.history_listitem, historyDetailArrayList);
        this.historyDetailArrayList = historyDetailArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryDetail hDetail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_listitem, parent, false);
        }

        TextView Date, shoppingAmount;
        Date = convertView.findViewById(R.id.date);
        shoppingAmount = convertView.findViewById(R.id.shopppingAmount);


        Date.setText(hDetail.getDate());
        shoppingAmount.setText(hDetail.getTotalShopping());

//        storeSharedPreferences(historyDetailArrayList);



        return convertView;
    }


//    private void storeSharedPreferences(ArrayList<HistoryDetail> updatedList) {
//        // Update SharedPreferences with the new list
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("historyData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(updatedList);
//        editor.putString("historyData", json);
//        editor.apply();
//    }
}



