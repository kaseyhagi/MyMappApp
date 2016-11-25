package com.example.kasey.mymappapp;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity {

    private ListView addressListView;
    private ListAdapter addressListAdapter;
    private ArrayList<Address> myAddressList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        myAddressList = new ArrayList<>();
        addressListAdapter = new AddressListAdapter(this, myAddressList);
        addressListView = (ListView) findViewById(R.id.addressListView);
        addressListView.setAdapter(addressListAdapter);
        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String picked = "You selected" + String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(AddressListActivity.this, picked, Toast.LENGTH_SHORT).show();


            }
        });



    }
    public void addAddress(Address address){
        myAddressList.add(address);
    }





}
