package com.example.posscanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Bill_adapter extends RecyclerView.Adapter<Bill_adapter.ViewHolder> {
    private ArrayList<Products> productDetailArrayList;

    public Bill_adapter(ArrayList<Products> productDetailArrayList) {
        this.productDetailArrayList = productDetailArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products productDetail = productDetailArrayList.get(position);

        holder.billProduct.setText(productDetail.getP_name());
        holder.BillQty.setText(String.valueOf(productDetail.NoOfProduct));
        holder.BillPrice.setText(productDetail.getPrice());
    }

    @Override
    public int getItemCount() {
        return productDetailArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView billProduct, BillQty, BillPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billProduct = itemView.findViewById(R.id.bill_produt);
            BillQty = itemView.findViewById(R.id.bill_qty);
            BillPrice = itemView.findViewById(R.id.bill_price);
        }
    }
}
