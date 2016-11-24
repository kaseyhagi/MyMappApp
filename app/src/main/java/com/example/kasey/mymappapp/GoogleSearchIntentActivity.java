package com.example.kasey.mymappapp;

import android.app.SearchManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;

public class GoogleSearchIntentActivity extends AppCompatActivity {

    LatLng currentLatLng;
    private String searchField;
    protected ListView addressListView;
    private List<Address> addressList;
    // Search bar EditText view
    protected EditText searchFieldEditText;
    protected ListAdapter addressAdapter;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_search_intent);

        // Getting search fields from previous activity through intent
        Intent intent = getIntent();
        searchField = intent.getExtras().getString("search");
        double lat = intent.getExtras().getDouble("currentLat");
        double lng = intent.getExtras().getDouble("currentLong");
        currentLatLng = new LatLng(lat, lng);

        initLayout();
    }
    public void initLayout(){

        try {
            onNewSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address[] addressArray = {addressList.get(0),addressList.get(1),addressList.get(2), addressList.get(3)};
        addressAdapter = new AddressListAdapter(this, addressArray);
        addressListView = (ListView)findViewById(R.id.addressListView);
        addressListView.setAdapter(addressAdapter);
        addressListView.setOnItemClickListener(new
                                                       AdapterView.OnItemClickListener() {
                                                           @Override
                                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                               String picked = "You selected" + String.valueOf(parent.getItemAtPosition(position));
                                                               Toast.makeText(GoogleSearchIntentActivity.this, picked, Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
        searchFieldEditText = (EditText) findViewById(R.id.search_bar);
        searchFieldEditText.setHint(searchField);
    }
    public void onSearch(View view) throws IOException {
        searchField = searchFieldEditText.getText().toString();
        initLayout();
    }
    public void onNewSearch() throws IOException {

        LatLngBounds currentBounds = toBounds(currentLatLng, 50);
        searchField = searchField.trim();
        addressList = null;

        Geocoder geocoder = new Geocoder(this);
        try {
            // add location to addressList
            addressList = geocoder.getFromLocationName(searchField,
                    4 /* number of search results*/,
                    currentBounds.southwest.latitude,
                    currentBounds.southwest.longitude,
                    currentBounds.northeast.latitude,
                    currentBounds.northeast.longitude);
//            this.addToList(addressList.get(0));
//            this.addToList(addressList.get(1));
//            this.addToList(addressList.get(2));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public LatLngBounds toBounds(LatLng center, double radius) {
        //radius in miles
        //THERE IS A PROBLEM IN THIS BLOCK WHERE? WHO KNOWS?
//        double earthRadius = 3959;
        double centerLat = center.latitude;
        double centerLng = center.longitude;
        double latChange = radius * 180 / (Math.PI * 3959);
        double longChange = latChange / Math.sin(centerLat);
        double sw_lat = centerLat - latChange;
        double ne_lat = centerLat + latChange;
        double sw_lng = centerLng - longChange;
        double ne_lng = centerLng + longChange;
        LatLng southwest = new LatLng(sw_lat, sw_lng);
        LatLng northeast = new LatLng(ne_lat, ne_lng);
//        LatLng southwest = new LatLng(21.34050, -158.063491);
//        LatLng northeast = new LatLng(21.444555, -157.85749);

        return new LatLngBounds(southwest, northeast);
    }

//    public void addToList(Address address) {
//        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(lparams);
//        tv.setText(searchField);
//        this.addressLinearLayout.addView(tv);
//
//        // line 2
//        TextView tv1 = new TextView(this);
//        tv1.setLayoutParams(lparams);
//        tv1.setText(address.getAddressLine(0));
//        this.addressLinearLayout.addView(tv1);
////line 3
//        TextView tv2 = new TextView(this);
//        tv2.setLayoutParams(lparams);
//        tv2.setText(address.getAddressLine(1));
//        this.addressLinearLayout.addView(tv2);
//
//    }
}
