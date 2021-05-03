package com.example.bhavdeepsinghassignment2.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhavdeepsinghassignment2.R;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment register.
     */
    // TODO: Rename and change types and number of parameters
    public static register newInstance(String param1, String param2) {
        register fragment = new register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View view;
    EditText emailR,usernameR,pwdR,confirmpwdR;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        emailR=(EditText) view.findViewById(R.id.email);
        usernameR=(EditText) view.findViewById(R.id.username);
        pwdR=(EditText) view.findViewById(R.id.password);
        confirmpwdR=(EditText) view.findViewById(R.id.retype_password);
        MaterialButton register=(MaterialButton) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateUsername() |!confirmPassword() | !validatePassword()) {
                    return;
                }
                Bundle b=new Bundle();
                b.putString("name",usernameR.getText().toString().trim());
                b.putString("email",emailR.getText().toString().trim());
                registerResult res=new registerResult();
                res.setArguments(b);
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.fragment2, res);
                ft.commit();

            }
        });
        return view;
    }
    private boolean validateEmail() {
        String emailInput = emailR.getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailR.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailR.setError("Please enter a valid email address");
            Toast.makeText(getContext(),"Please enter a valid email id",Toast.LENGTH_LONG).show();
            return false;
        } else {
            emailR.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String usernameInput = usernameR.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            usernameR.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            usernameR.setError("Username too long");
            Toast.makeText(getContext(),"Username should contain characters less than 15",Toast.LENGTH_LONG).show();
            return false;
        } else {
            usernameR.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = pwdR.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            pwdR.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pwdR.setError("Password too weak(");
            Toast.makeText(getContext()," Your password must be greater than equal to 8 characters,also atleast use once [0-9],[A-Z],[a-z],[@#$%^&+=]",Toast.LENGTH_LONG).show();
            return false;
        } else {
            pwdR.setError(null);
            return true;
        }
    }
    private boolean confirmPassword() {
        String passwordInputt = confirmpwdR.getText().toString().trim();
        if (passwordInputt.isEmpty()) {
            confirmpwdR.setError("Field can't be empty");
            return false;
        } else if (!pwdR.getText().toString().equals(passwordInputt)) {
            confirmpwdR.setError("Not matching with password");
            return false;
        } else {
            confirmpwdR.setError(null);
            return true;
        }
    }

}