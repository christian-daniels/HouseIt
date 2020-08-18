package com.example.houseit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText FullName, Password, Email;
    Button RegisterButton;
    TextView LoginButton;
    ProgressBar ProgressBar;
    FirebaseAuth fAuth; // registers the user
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // link class variables to physical attributes
        FullName        = findViewById(R.id.firstName);
        Password        = findViewById(R.id.password);
        Email           = findViewById(R.id.email);
        RegisterButton  = findViewById(R.id.registerButton);
        LoginButton     = findViewById(R.id.createText);


        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        ProgressBar     = findViewById(R.id.progressBar);

        // if the current user exists then send them to the Main Activity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        // When the register button is pressed
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memail    = Email.getText().toString().trim();
                final String mpassword = Password.getText().toString().trim();
                final String mname     = FullName.getText().toString();
                if (TextUtils.isEmpty(memail)){
                    Email.setError("Email sector cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(mpassword)){
                    Password.setError("Password is required");
                    return;
                }

                if(mpassword.length() < 6){
                    Password.setError("Password must be greater than 6 characters");
                    return;
                }

                // Turn progress bar on
                ProgressBar.setVisibility(View.VISIBLE);


                //register the user in firebase - notify if the process was successful or not
                fAuth.createUserWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Successfully Created.", Toast.LENGTH_SHORT).show();
                            // create a unique id for each user
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();

                            user.put("name", mname);
                            user.put("email", memail);

                            // insert into database and log success
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+userID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            ProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });




    }
}
