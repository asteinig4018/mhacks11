package com.mhacks11app.mhacks11app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;


public class LocationCreatePage extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_create_page);
        db = FirebaseFirestore.getInstance();
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherInputs();
            }
        });
    }

    private void gatherInputs() {
        //list for firestore
        Map<String, Object> data1 =new HashMap<>();

        //get location
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        GeoPoint placeOne = new GeoPoint(latitude,longitude);

        data1.put("location",placeOne);

        // Reference checkboxes and do stuff
        CheckBox busyCheckbox = findViewById(R.id.busy_Check);
        boolean isBusy = busyCheckbox.isChecked();

        // Do this for all inputs
        Toast.makeText(this, "Busy checked: " + isBusy, Toast.LENGTH_SHORT).show();

        //create document
        db.collection("spots")
                .add(data1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       // Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
