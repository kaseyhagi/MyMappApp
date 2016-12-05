package com.example.kasey.mymappapp;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kasey on 11/29/2016.
 */

public class Place {

    private String name;
    private String addressLine1;
    private String addressLine2;
    private LatLng latlng;

    public Place(Address addr){
        this.name = addr.getAddressLine(0);
        this.latlng= new LatLng (addr.getLatitude(), addr.getLongitude());
        this.addressLine1 = addr.getAddressLine(1);
        this.addressLine2 = addr.getAddressLine(2);
    }

    public String getName(){
        return this.name;
    }
    public double getLatitude(){
        return this.latlng.latitude;
    }
    public double getLongitude(){
        return this.latlng.longitude;
    }
    public LatLng getLatLng(){
        return this.latlng;
    }

    public String getAddressLine1(){
        return this.addressLine1;
    }
    public String getAddressLine2(){
        return this.addressLine2;
    }
}
