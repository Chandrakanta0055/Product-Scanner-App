// SellerListAdapter.java

package com.example.posscanner;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellerListAdapter extends ArrayAdapter<ProductDetail> {
    private ArrayList<ProductDetail> productDetailArrayList;
    String deleteUrl = "https://creativecollege.in/TechFest/delete.php";
    String updateUrl = "https://creativecollege.in/TechFest/edit.php";
    public Context context;

    ListView listView;
    Dialog dialog;

    public SellerListAdapter(Context context, ArrayList<ProductDetail> productDetailArrayList) {
        super(context, R.layout.seller_box_item, productDetailArrayList);
        this.productDetailArrayList = productDetailArrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductDetail productDetail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.seller_box_item, parent, false);
        }

        TextView BrandName, ProductName, Quantity, Price;
        Button deleteButton;
        LinearLayout Slview;

        BrandName = convertView.findViewById(R.id.S_Brand_name);
        ProductName = convertView.findViewById(R.id.S_product_name);
        Quantity = convertView.findViewById(R.id.S_product_quantity);
        Price = convertView.findViewById(R.id.S_product_price);
        deleteButton = convertView.findViewById(R.id.S_delete_button);
        Slview = convertView.findViewById(R.id.Slview);
        listView = convertView.findViewById(R.id.seller_listview);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.seller_dialog_box);

        BrandName.setText(productDetail.getBrandName());
        ProductName.setText(productDetail.getProductName());
        Quantity.setText(productDetail.getQuantity());
        Price.setText(String.valueOf(productDetail.getPrice()));


        // Handle delete button click
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if productDetail is not null
                Toast.makeText(getContext(), "Send", Toast.LENGTH_SHORT).show();
                if (productDetail != null) {
                    Toast.makeText(getContext(), "request", Toast.LENGTH_SHORT).show();
                    // Sample JSON data to send in the delete request
                    JSONObject jsonRequest = new JSONObject();
                    try {
                        jsonRequest.put("Barcode", productDetail.getBarcodeNUMBER());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Make a POST request using Volley for the delete operation
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            deleteUrl,
                            jsonRequest,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        boolean success = response.getBoolean("success");
                                        String message = response.getString("message");
                                        Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();

                                        if (success) {
                                            Log.d("TAG", "Delete success: " + message);

                                            // Remove the item from the ArrayList
                                            productDetailArrayList.remove(productDetail);

                                            // Notify the adapter that the dataset has changed
                                            notifyDataSetChanged();

                                            // Notify the ListView that the dataset has changed
                                            listView.invalidateViews();
                                            listView.refreshDrawableState();

                                            Toast.makeText(context, "Delete Success: " + productDetail.getBarcodeNUMBER(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("TAG", "Delete failed: " + message);
                                            Toast.makeText(context, "Delete failed: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("TAG", "Error parsing delete response JSON");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("TAG", "Error deleting item: " + error.toString());
                                    Toast.makeText(context, "Error deleting item: " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    // Add the delete request to the RequestQueue
                    Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
                }
            }
        });

        Slview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final int itemPosition = position;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.seller_dialog_box);

                EditText Bn = dialog.findViewById(R.id.S_BrandText);
                EditText Pn = dialog.findViewById(R.id.ProductNameText);
                EditText Pr = dialog.findViewById(R.id.S_PriceText);
                EditText Qt = dialog.findViewById(R.id.S_QuantityText);
                TextView Bv = dialog.findViewById(R.id.BarNum);
                Button update = dialog.findViewById(R.id.Update);

                Bn.setText(productDetailArrayList.get(itemPosition).getBrandName());
                Pn.setText(productDetailArrayList.get(itemPosition).getProductName());
                Pr.setText(String.valueOf(productDetailArrayList.get(itemPosition).getPrice()));
                Qt.setText(productDetailArrayList.get(itemPosition).getQuantity());
                Bv.setText(productDetailArrayList.get(itemPosition).getBarcodeNUMBER());

                // Inside Slview.setOnLongClickListener
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Check if productDetail is not null
                        if (productDetail != null) {
                            // Sample JSON data to send in the update request
                            JSONObject jsonRequest = new JSONObject();
                            try {
                                jsonRequest.put("Barcode", Bv.getText().toString());
                                jsonRequest.put("BrandName", Bn.getText().toString());
                                jsonRequest.put("ProductName", Pn.getText().toString());
                                jsonRequest.put("Quantity", Qt.getText().toString());
                                jsonRequest.put("Price", Double.parseDouble(Pr.getText().toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Make a POST request using Volley for the update operation
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.POST,
                                    updateUrl,
                                    jsonRequest,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Handle the update response
                                            try {
                                                String message = response.getString("message");

                                                if (message=="Update success") {
                                                    Log.d("TAG", "Update success: " + message);

                                                    // Create an instance of ProductDetail with updated values
                                                    ProductDetail updatedProductDetail = new ProductDetail(
                                                            Bv.getText().toString(),
                                                            Bn.getText().toString(),
                                                            Pn.getText().toString(),
                                                            Qt.getText().toString(),
                                                            Double.parseDouble(Pr.getText().toString())
                                                    );

                                                    // Update the item in the ArrayList
                                                    productDetailArrayList.set(itemPosition, updatedProductDetail);

                                                    // Notify the adapter that the dataset has changed
                                                    notifyDataSetChanged();

                                                    Toast.makeText(context, "Update Success: " + Bv.getText().toString(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e("TAG", "Update failed: " + message);
                                                    Toast.makeText(context, "Update failed: " + message, Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.e("TAG", "Error parsing update response JSON");
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Handle errors
                                            Log.e("TAG", "Error updating item: " + error.toString());
                                            Toast.makeText(context, "Error updating item: " + error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );

                            // Add the update request to the RequestQueue
                            Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            }
        });

        return convertView;
    }
}
