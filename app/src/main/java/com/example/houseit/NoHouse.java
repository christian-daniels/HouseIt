package com.example.houseit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.houseit.models.House;
import com.example.houseit.views.Dashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import javax.annotation.Nullable;

public class NoHouse extends AppCompatActivity {
    public static final String TAG = "TAG";
    // This activity is for users that have not joined a room yet
    // a user may create or join a house hold.
    // if a household a user was trying to join does not exist the user is notified
    // if a user creates a house they are taken to a newly created Dashboard
    // if a user joins a house they are taken to that house's Dashboard

    TextView fName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button createHouse, joinHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nohouse);
        fName           = findViewById(R.id.firstName);
        createHouse     = findViewById(R.id.createButton);
        joinHouse       = findViewById(R.id.joinButton);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();

        userId          = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();


        // this makes a doc ref of all the users in the database
        DocumentReference userReference = fStore.collection("users").document(userId);
        userReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String fullName = documentSnapshot != null ? documentSnapshot.getString("name") : null;
                if (fullName != null){
                    String[] separated = fullName.split(" ");
                    String firstName = separated[0];
                    fName.setText(firstName);
                }
            }
        });
//      This was an example: here in order to create an entry for "codes" i had to set in a map object
//        Map<String, Object> city = new HashMap<>();
//        city.put("name", "Los Angeles");
//        city.put("state", "CA");
//        city.put("country", "USA");
//        fStore.collection("codes").document("ab").set(city);




        DocumentReference houseCodeReference = fStore.collection("houses").document();
        final String nextDocId = houseCodeReference.getId();
        // User presses Create a house button - adds house parameter to
        createHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference dReference = fStore.collection("houses").document(nextDocId);
                House newHouse = new House(userId);
                newHouse.setHouseCode(nextDocId);
                dReference.set(newHouse);
                startActivity(new Intent(getApplicationContext(), Dashboard.class));


            }
        });
        joinHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // flow: someone invites you - they give you a link to join their house
                // copy and paste the code into a box that pops onto the screen
                // code is taken and placed into the fstore stuff.

                final DocumentReference dReference = fStore.collection("houses").document("jtuJRqkGxLhbGHqvAKaP");
                dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            House house = documentSnapshot.toObject(House.class);
                            house.addUser(userId);
                            Toast.makeText(NoHouse.this, "House Exists, user Added", Toast.LENGTH_SHORT).show();

                            dReference.set(house);
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));

                        } else {
                            Toast.makeText(NoHouse.this, "House does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }



    public void logout(View view) {
        //logout
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
