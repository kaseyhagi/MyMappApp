package com.example.kasey.mymappapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView search_f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_f=(TextView)findViewById(R.id.search_field);
        Intent intent = getIntent();
        String search_tf= intent.getExtras().getString("search");
        search_f.setText(search_tf);
    }
    public void onLocationSearch(View view){
        final double searchLat = 21.397222;
        final double searchLong = -157.973333;
        String searchName = "Walmart";
        Boolean putLocation = true;

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("putLocation", putLocation);
        intent.putExtra("search_LocationLat",searchLat );
        intent.putExtra("search_LocationLong",searchLong);
        intent.putExtra("search_LocationName", searchName);
        startActivity(intent);
    }


}
