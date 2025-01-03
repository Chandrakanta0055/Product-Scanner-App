package com.example.posscanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.posscanner.databinding.FragmentNavigationBarBinding;

public class NavigationBar extends Fragment {

    FragmentNavigationBarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentNavigationBarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        Toast.makeText(getContext(), "New Fragement", Toast.LENGTH_LONG).show();

        // default open
        replaceFragment(new ScannerFragment());

        // set onclick listener on navigation view
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.scanBtn) {
                replaceFragment(new ScannerFragment());
            } else if (item.getItemId() == R.id.BagBtn) {
                replaceFragment(new BagFragment());
            } else if (item.getItemId() == R.id.historyBtn) {
                replaceFragment(new historyFragment());
            } else if (item.getItemId() == R.id.profileBtn) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        return view;
    }

    // Add this method to replace fragments
    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout2, fragment)
                    .commit();
        }
    }

}
