package com.example.houseit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {


    EditText FullName, Password, Email;
    Button RegisterButton;
    TextView LoginButton;
    ProgressBar ProgressBar;
    FirebaseAuth fAuth; // registers the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // link class variables to physical attributes
        FullName        = findViewById(R.id.fullName);
        Password        = findViewById(R.id.password);
        Email           = findViewById(R.id.email);
        RegisterButton  = findViewById(R.id.registerButton);
        LoginButton     = findViewById(R.id.createText);


        fAuth           = FirebaseAuth.getInstance();
        ProgressBar     = findViewById(R.id.progressBar);


        // When the register button is pressed
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Email.setError("Email sector cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                    return;
                }

                if(password.length() < 6){
                    Password.setError("Password must be greater than 6 characters");
                    return;
                }



            }
        });






    }
}
