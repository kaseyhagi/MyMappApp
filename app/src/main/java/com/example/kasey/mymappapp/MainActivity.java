package com.example.kasey.mymappapp;

//import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;





public class MainActivity extends AppCompatActivity {

    protected Button m_AddItemButton;
    protected EditText m_EditText;
    protected Button m_MapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_AddItemButton = (Button) findViewById(R.id.addButton);
        m_MapButton = (Button) findViewById(R.id.goToMap);
        m_EditText = (EditText) findViewById(R.id.itemEditText);
        TextView tv = new TextView(this);
        String text = "HELLO";
        tv.setText(text);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButtonClickHandler buttonClickHandler = new ButtonClickHandler();
        m_AddItemButton.setOnClickListener(buttonClickHandler);
        m_MapButton.setOnClickListener(buttonClickHandler);


    }

    private class ButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onPinLocationButtonClick(view);

        }

    }

    public void onPinLocationButtonClick(View view) {
        Boolean putLocation = false;
        Intent intent = new Intent(this, GoogleSearchIntentActivity.class);
        startActivity(intent);
    }

}

