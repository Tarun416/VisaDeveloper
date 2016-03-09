package com.example.visadeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.visadeveloper.Generator.ApiGenerator;
import com.example.visadeveloper.api.MvisaApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class MainActivity extends AppCompatActivity {

    Button b;
    MvisaApi mvisaApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       b= (Button)findViewById(R.id.confirm);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mvisaApi= ApiGenerator.createService(MvisaApi.class);

                JSONObject jsonObject = new JSONObject();

                JSONObject cardAcceptor=new JSONObject();

                JSONObject address=new JSONObject();

                try {
                    address.put("city", "Bangalore");
                    address.put("country", "IND");
                }

                catch (JSONException e)
                {

                }


               try {
                   cardAcceptor.put("address", address);
                   cardAcceptor.put("idCode", "ID-Code123");
                   cardAcceptor.put("name", "ABC");
               }
               catch (JSONException e)
               {

               }

               try {
                   jsonObject.put("acquirerCountryCode", "643");
                   jsonObject.put("acquiringBin", "400171");

                   jsonObject.put("amount", "112");
                   jsonObject.put("businessApplicationId", "CI");
                   jsonObject.put("cardAcceptor", cardAcceptor);

                   jsonObject.put("localTransactionDateTime", "2016-03-09T11:40:52");

                   jsonObject.put("merchantCategoryCode", "4829");
                   jsonObject.put("recipientPrimaryAccountNumber", "4123640062698797");
                   jsonObject.put("senderAccountNumber", "4541237895236");

                   jsonObject.put("senderName", "Mohammed Qasim");


                   jsonObject.put("senderReference", "1234");
                   jsonObject.put("systemsTraceAuditNumber", "313042");
                   jsonObject.put("transactionCurrencyCode", "USD");

                   jsonObject.put("transactionIdentifier", "2016-03-09T11:40:52");











               /* {
                    "acquirerCountryCode": "643",
                        "acquiringBin": "400171",
                        "amount": "124.05",
                        "businessApplicationId": "CI",
                        "cardAcceptor": {
                    "address": {
                        "city": "Bangalore",
                                "country": "IND"
                    },
                    "idCode": "ID-Code123",
                            "name": "Card Accpector ABC"
                },
                    "localTransactionDateTime": "2016-03-09T11:40:52",
                        "merchantCategoryCode": "4829",
                        "recipientPrimaryAccountNumber": "4123640062698797",
                        "retrievalReferenceNumber": "430000367618",
                        "senderAccountNumber": "4541237895236",
                        "senderName": "Mohammed Qasim",
                        "senderReference": "1234",
                        "systemsTraceAuditNumber": "313042",
                        "transactionCurrencyCode": "USD",
                        "transactionIdentifier": "381228649430015"
                }*/


              //  jsonObject.put("email", emailEditText.getText().toString());

                try {

                    TypedInput in = new TypedByteArray("application/json", jsonObject.toString().getBytes("UTF-8"));
                    mvisaApi.debit(in, new Callback<Response>() {
                        @Override
                        public void success(Response response1, Response response) {

                            if(response.getBody()!=null)
                            {

                            }


                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                }
                catch(UnsupportedEncodingException e)
                {

                }

               }
               catch (JSONException e)
               {

               }





        }});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
