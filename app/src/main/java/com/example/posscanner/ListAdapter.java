package com.example.posscanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Products> {
    private ArrayList<Products> productDetailArrayList;

    public ListAdapter(Context context, ArrayList<Products> productDetailArrayList) {
        super(context, R.layout.baglistitem, productDetailArrayList);
        this.productDetailArrayList = productDetailArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Products productDetail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.baglistitem, parent, false);
        }

        TextView BrandName, ProductName, Quantity, Price, NoOfProduct;
        Button deleteButton;

        BrandName = convertView.findViewById(R.id.Brand_name);
        ProductName = convertView.findViewById(R.id.product_name);
        Quantity = convertView.findViewById(R.id.product_quantity);
        Price = convertView.findViewById(R.id.product_price);
        NoOfProduct = convertView.findViewById(R.id.NoOfProduct);
        deleteButton = convertView.findViewById(R.id.delete_button);

        BrandName.setText(productDetail.getB_name());
        ProductName.setText(productDetail.getP_name());
        Quantity.setText(productDetail.getQuantity());
        Price.setText(productDetail.getPrice());
        NoOfProduct.setText(String.valueOf(productDetail.NoOfProduct));

        // Handle delete button click
        deleteButton.setOnClickListener(v -> {
            // Remove the item from the ArrayList
            productDetailArrayList.remove(productDetail);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();

            // Update SharedPreferences after removing the item
            updateSharedPreferences(productDetailArrayList);
        });

        return convertView;
    }

    private void updateSharedPreferences(ArrayList<Products> updatedList) {
        // Update SharedPreferences with the new list
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("BagData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(updatedList);
        editor.putString("BagItem", json);
        editor.apply();
    }
}
