package com.example.posscanner;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.CallableStatement;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    ImageView imageView;
    Button logout2, editProfile;
    TextView NAme,MNo,EMail,ADress;
    private final int GALLERY_REQ_CODE = 1000;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQ_CODE);
            }
        });

        SharedPreferences sharedPreferencess = getActivity().getSharedPreferences("ProfileData", Context.MODE_PRIVATE);

        NAme = view.findViewById(R.id.NAme);
        MNo = view.findViewById(R.id.MNo);
        EMail = view.findViewById(R.id.EMail);
        ADress = view.findViewById(R.id.Adre);
        NAme.setText(sharedPreferencess.getString("Name",null));
        MNo.setText(sharedPreferencess.getString("MobileNo",null));
        EMail.setText(sharedPreferencess.getString("EmailId",null));
        ADress.setText(sharedPreferencess.getString("Address",null));
//        Toast.makeText(getContext(), sharedPreferencess.getString("Name",null), Toast.LENGTH_SHORT).show();


        editProfile = view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        logout2 = view.findViewById(R.id.logout);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", null);
                editor.putString("loginn", null); // Corrected "Saller" to "Seller"
                editor.apply();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editors = sharedPreferences.edit();
                editors.putString("Name", null);
                editors.putString("MobileNo", null);
                editors.putString("Address", null);
                editors.putString("EmailId", null);
                editors.apply();
                Toast.makeText(getContext(), "Logout Success", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginSignup())
                        .commit();
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imageView.setImageURI(data.getData());
            }
        }
    }

    public void ShowDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_profile__detaiols);

        EditText Name, MobileNo, Address, Emaild;

        Name = dialog.findViewById(R.id.Name);
        MobileNo = dialog.findViewById(R.id.MobileNo);
        Address = dialog.findViewById(R.id.Address);
        Emaild = dialog.findViewById(R.id.EmailId);
        Button save = dialog.findViewById(R.id.Save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String mobileNo = MobileNo.getText().toString();
                String address = Address.getText().toString();
                String emailid = Emaild.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (mobileNo.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the mobileNumber", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(getContext(), "Enter the Adderss", Toast.LENGTH_SHORT).show();
                } else if (emailid.isEmpty()) {
                    Toast.makeText(getContext(), "Enter The Email", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name", name);
                    editor.putString("MobileNo", mobileNo);
                    editor.putString("Address", address);
                    editor.putString("EmailId", emailid);
                    editor.apply();
                    Toast.makeText(getContext(), "Save sucessfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}


