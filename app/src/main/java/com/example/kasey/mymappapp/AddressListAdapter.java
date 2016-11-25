package com.example.kasey.mymappapp;

import android.content.Context;
import android.location.Address;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kasey on 11/24/2016.
 */

public class AddressListAdapter extends ArrayAdapter<Address> {
    AddressListAdapter(Context context, ArrayList<Address> values){
        super(context, R.layout.address_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View theView = layoutInflater.inflate(R.layout.address_layout, parent, false);

        Address address = getItem(position);

        TextView textView1 = (TextView) theView.findViewById(R.id.addressLine1);
        TextView textView2 = (TextView) theView.findViewById(R.id.addressLine2);
        TextView textView3 = (TextView) theView.findViewById(R.id.addressLine3);

        textView1.setText(address.getAddressLine(0));
        textView2.setText(address.getAddressLine(1));
        textView3.setText(address.getAddressLine(2));

        return theView;
    }
}
