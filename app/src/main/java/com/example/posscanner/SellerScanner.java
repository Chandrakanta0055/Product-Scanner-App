package com.example.posscanner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import java.util.List;

public class SellerScanner extends Fragment {

    private CompoundBarcodeView barcodeView;
    private TextView scannedBarcodeTextView;

    public SellerScanner() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_scanner, container, false);

        // Initialize UI elements
        barcodeView = view.findViewById(R.id.barcodeScannerView);
        scannedBarcodeTextView = view.findViewById(R.id.scannedBarcodeTextView);

        // Set up barcode scanner callback
        barcodeView.decodeSingle(result -> {
            if (result != null && result.getText() != null) {
                String barcodeNumber = result.getText();
                // Update the TextView with the scanned barcode number
                scannedBarcodeTextView.setText("Scanned Barcode: " + barcodeNumber);
                Toast.makeText(getContext(), "Scann Susess"+barcodeNumber, Toast.LENGTH_LONG).show();

                // Create a new instance of InputProductDetail
                InputProductDetail inputProductDetail = new InputProductDetail();
                Bundle bundle = new Bundle();
                bundle.putString("key", barcodeNumber);
                inputProductDetail.setArguments(bundle);

                // Get the FragmentManager and perform the fragment transaction
                if (getFragmentManager() != null) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, inputProductDetail);
                    transaction.addToBackStack(null); // Optionally add the transaction to the back stack
                    transaction.commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume the barcode scanner view when the fragment is resumed
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause the barcode scanner view when the fragment is paused to release the camera resources
        barcodeView.pause();
    }
}
