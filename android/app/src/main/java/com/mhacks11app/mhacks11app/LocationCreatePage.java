package com.mhacks11app.mhacks11app;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class LocationCreatePage extends AppCompatActivity {
    FirebaseFirestore db;

    private static final String TAG = "Oppur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_create_page);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherInputs();
            }
        });

    }

    private void takePhoto(){
        //launch camera, take a friggin photo dude
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
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

        //Gather Checkbox inputs

        CheckBox busyCheckbox = findViewById(R.id.busy_Check);
        boolean isBusy = busyCheckbox.isChecked();

        CheckBox quietCheckbox = findViewById(R.id.quiet_Check);
        boolean isQuiet = quietCheckbox.isChecked();

        CheckBox landscapeCheck = findViewById(R.id.landscape_Check);
        boolean goodLandscape = landscapeCheck.isChecked();

        CheckBox portraitCheck = findViewById(R.id.portrait_Check);
        boolean goodPortrait = portraitCheck.isChecked();

        CheckBox cityCheck = findViewById(R.id.city_Check);
        boolean goodCityscape = cityCheck.isChecked();

        CheckBox otherCheck = findViewById(R.id.other_Check);
        boolean isOther = otherCheck.isChecked();

        CheckBox sunriseCheck = findViewById(R.id.sunrise_Check);
        boolean sunrise = sunriseCheck.isChecked();

        CheckBox daytimeCheck = findViewById(R.id.daytime_Check);
        boolean daytime = daytimeCheck.isChecked();

        CheckBox sunsetCheck = findViewById(R.id.sunset_Check);
        boolean sunset = sunsetCheck.isChecked();

        // Print user input to file

        if (isBusy){
            data1.put("activity level 1", "busy");
        }

        if (isQuiet) {
            data1.put("activity level 2", "quiet");
        }

        if (goodLandscape){
            data1.put("style1", "landscape");
        }

        if (goodPortrait){
            data1.put("style2", "portrait");
        }

        if (goodCityscape){
            data1.put("style3", "cityscape");
        }

        if (isOther){
            data1.put("style4", "other");
        }

        if (sunrise){
            data1.put("ToD1", "sunrise");
        }

        if (daytime){
            data1.put("ToD2", "daytime");
        }

        if (sunset){
            data1.put("ToD3", "sunset");
        }

        //reading user title from strings.xml and assigning to string "title"
        EditText inputTitle = (EditText) findViewById(R.id.name_prompt);
        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        String title = inputTitle.getText().toString();

        data1.put("Title", title);


        // Do this for all inputs
        //Toast.makeText(this, "Title equals: " + title, Toast.LENGTH_LONG).show();

        //create document
        db.collection("spots")
                .add(data1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        Intent intent = new Intent(this,MainActivity.class);
        //EditText editText= (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);}



    }

