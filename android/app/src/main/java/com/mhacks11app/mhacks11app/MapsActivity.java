package com.mhacks11app.mhacks11app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "MapsActivity";

    public void centerMapOnLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null){

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }



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

        centerMapOnLocation();



        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!");

        db.collection("spots")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String[] fields = { "ToD1", "ToD2", "activity level 1", "activity level 2",
                                            "style1", "style2", "style3", "style4" };
                        String infoSnippet = "";

                        if (task.isSuccessful()) {
                            Log.d(TAG, "Task was succesful");
                            Log.d(TAG,  "size:" + task.getResult().size());
                            int counter = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                infoSnippet="";
                                if (document != null) {
                                    LocationInfo loc = document.toObject(LocationInfo.class);
                                    Log.d(TAG, "!!!!!!!!!!!!");
                                    LatLng sydney = new LatLng(loc.getLocation().getLatitude(), loc.getLocation().getLongitude());
                                    Log.d(TAG, "" + loc.getLocation().getLatitude());
                                    Log.d(TAG, "" + loc.getLocation().getLongitude());

                                    Log.d(TAG, "HHHHHHHHHHHHHH");
                                    for (int i = 0; i < 8; i++) {
                                        if (document.getString("" + fields[i]) != null) {
                                            infoSnippet += document.getString("" + fields[i]) + "; ";
                                        }
                                    }
                                    infoSnippet = infoSnippet.substring(0, infoSnippet.length() - 2);
                                    MarkerOptions markerOptions=new MarkerOptions();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(sydney)
                                            .title("" + document.getString("Title"))
                                            .snippet(infoSnippet)
                                            .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE)));
                                    Log.d(TAG, "IIIIIIIIIIIIII");
                                    Log.d(TAG, "" + infoSnippet);

                                    //CustonInfoWindowGoogleMap customInfoWindow = new CustonInfoWindowGoogleMap(this);
                                    //mMap.setInfoWindowAdapter(customInfoWindow);
                                    //Marker m = mMap.addMarker(markerOptions);
                                    //m.setTag(info);
                                    //m.showInfoWindow();


                                        //mMap.addMarker(new MarkerOptions()
                                }

                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ");
                        }
                        Log.d(TAG, "no error");
                    }
                });


    }
}


