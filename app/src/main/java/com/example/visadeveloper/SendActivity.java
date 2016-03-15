package com.example.visadeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hp on 14-03-2016.
 */
public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

     TextView tv=(TextView) findViewById(R.id.amt);

        Intent i =getIntent();
       String amt= i.getStringExtra("amt");

        tv.setText("Rs:"+""+amt);




        // AIzaSyC_IPD6o5iSyz90RtS_O7RdvlJRgpL1iRQ


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       this.finish();
    }
}
