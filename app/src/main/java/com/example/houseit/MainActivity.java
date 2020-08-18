package com.example.houseit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    // This activity is for users that have not joined a room yet
    // a user may create or join a house hold.
    // if a household a user was trying to join does not exist the user is notified
    // if a user creates a house they are taken to a newly created dashboard
    // if a user joins a house they are taken to that house's dashboard

    TextView fName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button createHouse, joinHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                boolean creator = true;
                DocumentReference dReference = fStore.collection("houses").document(nextDocId);
                House newHouse = new House(userId, creator);
                newHouse.setHouseCode(nextDocId);
                dReference.set(newHouse);



            }
        });
        joinHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // flow: someone invites you - they give you a link to join their house
                // copy and paste the code into a box that pops onto the screen
                // code is taken and placed into the fstore stuff.
                boolean creator = false;
                CollectionReference collectionReference = fStore.collection("houses");
                House newHouse = new House(userId, creator);
                // update House the person is joining
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
