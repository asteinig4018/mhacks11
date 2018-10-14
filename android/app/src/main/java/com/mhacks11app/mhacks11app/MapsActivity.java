package com.mhacks11app.mhacks11app;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        final ArrayList<LocationInfo> locationList = new ArrayList<LocationInfo>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);



        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!");

        db.collection("spots")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Task was succesful");
                            Log.d(TAG,  "size:" + task.getResult().size());
                            int counter = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                LocationInfo loc = document.toObject(LocationInfo.class);
                                //locationList.add(loc);

                                //Log.d(TAG, " " + loc.getLocation().getLatitude());

                                // Add a marker in Sydney and move the camera
                                LatLng sydney = new LatLng(loc.getLocation().getLatitude(), loc.getLocation().getLatitude());
                                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                                counter++;
                                //Log.d(TAG, document.getId() + " => " + document.get("location"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ");
                        }
                        Log.d(TAG, "no error");
                    }
                });


            //for (int i = 0; i < locationList.size() - 1; i++) {
                //Log.d(TAG, locationList.get(i).getTitle());
            //}


    }
}
