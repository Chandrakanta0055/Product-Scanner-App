package com.example.posscanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posscanner.databinding.FragmentNavigationBarBinding;
import com.example.posscanner.databinding.FragmentSellerNavigationBinding;


public class SellerNavigationFragment extends Fragment {


    public SellerNavigationFragment() {
        // Required empty public constructor
    }

    FragmentSellerNavigationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSellerNavigationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        replaceFragment(new SellerScanner());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId()==R.id.scan)
            {
                replaceFragment(new SellerScanner());
            }
            else if(item.getItemId()==R.id.Box)
            {
                replaceFragment(new BoxFragment());
            } else if (item.getItemId()==R.id.Profile) {
                replaceFragment(new ProfileFragment());

            }


            return true;
        });





         return view;
    }
    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.SellerFrameLayout, fragment)
                    .commit();
        }
    }
}