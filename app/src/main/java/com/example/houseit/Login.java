package com.example.houseit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Password, Email;
    Button LoginButton;
    TextView RegisterButton;
    android.widget.ProgressBar ProgressBar;
    FirebaseAuth fAuth; // authenticate email and password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // link class variables to physical attributes
        Password        = findViewById(R.id.password);
        Email           = findViewById(R.id.email);
        LoginButton  = findViewById(R.id.registerButton);
        RegisterButton     = findViewById(R.id.createText);


        fAuth           = FirebaseAuth.getInstance();
        ProgressBar     = findViewById(R.id.progressBar);

        LoginButton.setOnClickListener(new View.OnClickListener() {
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

                // Turn progress bar on
                ProgressBar.setVisibility(View.VISIBLE);

                //Authenticate user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "User Successfully Signed In.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), NoHouse.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }
}
