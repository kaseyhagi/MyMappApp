package com.example.kasey.mymappapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import android.support.annotation.NonNull;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    EditText searchBar;
    LatLng newLocation;
    String newLocationName;
    ArrayList<Address> addressList;
    LatLngBounds bounds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchBar = (EditText)findViewById(R.id.map_search_bar);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Intent intent = getIntent();
        //if navigating from "search" then pins to location clicked
        // putLocation is true when navigating from search
        // putLocation is false when navigating from main
        if(intent.getExtras().getBoolean("putLocation")){
            newLocation = new LatLng(
                    intent.getExtras().getDouble("search_LocationLat"),
                    intent.getExtras().getDouble("search_LocationLong"));
            newLocationName = intent.getExtras().getString("search_LocationName");
        }
        else if(intent.getExtras().getBoolean("putLocationByAddress")){
            newLocation = new LatLng(intent.getExtras().getDouble("addressLat"),
                    intent.getExtras().getDouble("addressLng"));
            newLocationName = intent.getExtras().getString("addressName");
        }

        else if(intent.getExtras().getBoolean("multiplePins")) {
            addressList =intent.getExtras().getParcelableArrayList("addresses");

        }
        else {
                newLocation=null;
                newLocationName=null;
         }
        }






    public void onSearch(View view) {
        String searchEntry = this.searchBar.getText().toString();
        if(!searchEntry.isEmpty()){
            Intent intent = new Intent(this, GoogleSearchIntentActivity.class);
            intent.putExtra("search", searchEntry);
//            intent.putExtra("currentLat", mLastLocation.getLatitude());      /* use when mLastLocation works*/
//            intent.putExtra("currentLong", mLastLocation.getLongitude());
            intent.putExtra("currentLat",21.3972);
            intent.putExtra("currentLong",-157.9745 );
            startActivity(intent);
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

        //Setting up info window for pins
        if(mMap != null){
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
                @Override
                public View getInfoWindow(Marker marker){
                    return null;
                }
                @Override
                public View getInfoContents(Marker marker){
                    View infoWindow = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView locationName = (TextView)infoWindow.findViewById(R.id.iw_location1);
                    TextView location2 = (TextView)infoWindow.findViewById(R.id.iw_location2);

                    locationName.setText(marker.getTitle());
                    location2.setText(marker.getPosition().toString());
                    return infoWindow;
                }
            });
        }
        //close setting up info window
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); */
        if(newLocation!= null){
            Marker searchResultMarker = mMap.addMarker(new MarkerOptions()
                    .position(newLocation)
                    .title(newLocationName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
            mMap.setMinZoomPreference(15.0f);
            mMap.setMaxZoomPreference(20.0f);
            searchResultMarker.showInfoWindow();
            Toast.makeText(MapsActivity.this, "new location", Toast.LENGTH_SHORT).show();

        }
        createPins();
//            createBounds();

    }
    private void createBounds(){
        //used for debuggin purposes to create pins of the bounds
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.3682554, -158.02622019 ))
                .title("southwest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.42614457,-157.92277980))
                .title("northeast")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

    }
    /* Create pins from ArrayList<Address>*/
    private void createPins(){
        if(addressList!=null){
            for(int i=0;i<addressList.size();i++){
                Address address = addressList.get(i);
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(address.getLatitude(),address.getLongitude()))
                        .title(address.getAddressLine(0))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

            }
            mMap.setMinZoomPreference(11.0f);
            mMap.setMaxZoomPreference(50.0f);
//            change numbers in next line to set camera to current location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(21.3972, -157.9745 )));
            Toast.makeText(MapsActivity.this, "Created Pins", Toast.LENGTH_SHORT).show();

        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)   //provides callbacks when client connected or disconnected
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)   //adds location services API endpoint from Goole Play Services
                .build();
        mGoogleApiClient.connect(); // a client must be connected before executing any operation
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//
//
//    }
@Override
public void onConnected(Bundle connectionHint) {
//    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//            mGoogleApiClient);
//    if (mLastLocation != null) {
//        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//    }
}

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){}

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }

            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
    }
}
