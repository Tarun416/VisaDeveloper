package com.example.visadeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visadeveloper.Generator.ApiGenerator;
import com.example.visadeveloper.api.MvisaApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class MainActivity extends AppCompatActivity {

    Button b;
    MvisaApi mvisaApi;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    final EditText amount=(EditText) findViewById(R.id.amt);
        final EditText merchantid=(EditText) findViewById(R.id.merid);
        final EditText senderac=(EditText) findViewById(R.id.start1);
        setSupportActionBar(toolbar);
        Logger.init("Tag").setMethodCount(3);

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

                if(!TextUtils.isEmpty(senderac.getText().toString())&&!TextUtils.isEmpty(merchantid.getText().toString())&&!TextUtils.isEmpty(amount.getText().toString()) ) {

                    if (checkPlayServices()) {

                        // Register Device in GCM Server
                        Intent intent = new Intent(getApplicationContext(), RegistrationIntentService.class);
                      //  intent.putExtra("fullname", fullname.getText().toString());
                      //  intent.putExtra("accountno", accountNo.getText().toString());
                        startService(intent);
                    }


                    InputStream cert = getApplicationContext().getResources().openRawResource(R.raw.cert);
                    BufferedInputStream bis = new BufferedInputStream(cert);

                    InputStream bundle = getApplicationContext().getResources().openRawResource(R.raw.myapp_keyandcertbundle);
                    mvisaApi = ApiGenerator.createService(MvisaApi.class, bis, bundle);

                    JSONObject jsonObject = new JSONObject();

                    JSONObject cardAcceptor = new JSONObject();

                    JSONObject address = new JSONObject();

                    try {
                        address.put("city", "Bangalore");
                        address.put("country", "IND");
                    } catch (JSONException e) {

                    }


                    try {
                        cardAcceptor.put("address", address);
                        cardAcceptor.put("idCode", "ID-Code123");
                        cardAcceptor.put("name", "ABC");
                    } catch (JSONException e) {

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

                        jsonObject.put("transactionIdentifier", "381228649430015");











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





                  /* {
                       "acquirerCountryCode": "643",
                           "acquiringBin": "400171",
                           "amount": "124.05",
                           "businessApplicationId": "CO",
                           "cardAcceptor": {
                       "address": {
                           "city": "mVisa cashout",
                                   "country": "IND"
                       },
                       "idCode": "CA-IDCode-77765",
                               "name": "mVisa cashout"
                   },
                       "localTransactionDateTime": "2016-03-10T14:06:59",
                           "merchantCategoryCode": "6012",
                           "recipientPrimaryAccountNumber": "4123640062698797",
                           "retrievalReferenceNumber": "412123412878",
                           "senderAccountNumber": "456789123456",
                           "senderName": "Mohammed Qasim",
                           "senderReference": "REFNUM123",
                           "systemsTraceAuditNumber": "567889",
                           "transactionCurrencyCode": "USD",
                           "transactionIdentifier": "381228649430015"
                   }*/

                        try {

                            String json = "{ \"retrievalReferenceNumber\":\"430000367618\",\"secondaryId\":\"\",\"recipientName\":\"Aka\",\"acquirerCountryCode\":\"643\",\"transactionFeeAmt\":\"10\",\"acquiringBin\":\"400171\",\"amount\":\"10\",\"businessApplicationId\":\"CI\",\"cardAcceptor\":{\"name\":\"Card Accpector ABC\",\"idCode\":\"ID-Code123\",\"address\":{\"city\":\"Bangalore\",\"country\":\"IND\",\"state\":\"Karnataka\"}},\"localTransactionDateTime\":\"2016-03-13T11:40:52\",\"merchantCategoryCode\":\"4829\",\"recipientPrimaryAccountNumber\":\"4123640062698797\",\"senderAccountNumber\":\"4541237895236\",\"senderName\":\"Mohammed Qasim\",\"senderReference\":\"1234\",\"systemsTraceAuditNumber\":\"313042\",\"transactionCurrencyCode\":\"USD\",\"transactionIdentifier\":\"381228649430015\"}";

                            String json1 = "{ \"retrievalReferenceNumber\":\"430000367618\",\"secondaryId\":\"\",\"recipientName\":\"Aka\",\"acquirerCountryCode\":\"643\",\"transactionFeeAmt\":\"10\",\"acquiringBin\":\"400171\",\"amount\":\"10\",\"businessApplicationId\":\"CO\",\"cardAcceptor\":{\"name\":\"Card Accpector ABC\",\"idCode\":\"ID-Code123\",\"address\":{\"city\":\"Bangalore\",\"country\":\"IND\",\"state\":\"Karnataka\"}},\"localTransactionDateTime\":\"2016-03-13T11:40:52\",\"merchantCategoryCode\":\"4829\",\"recipientPrimaryAccountNumber\":\"4123640062698797\",\"senderAccountNumber\":\"4541237895236\",\"senderName\":\"Mohammed Qasim\",\"senderReference\":\"1234\",\"systemsTraceAuditNumber\":\"313042\",\"transactionCurrencyCode\":\"USD\",\"transactionIdentifier\":\"381228649430015\"}";
                            //  String json{
                            final TypedInput in1 = new TypedByteArray("application/json", json1.getBytes("UTF-8"));
                            final TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
                            mvisaApi.debit(in, new Callback<Response>() {
                                @Override
                                public void success(Response response1, Response response) {
                                    Logger.d("ss", response1.toString());
                                    JSONObject responseJson = new JSONObject();
                                    try {
                                        responseJson = new JSONObject(new String(((TypedByteArray) response.getBody()).getBytes()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    mvisaApi.push(in1, new Callback<Response>() {
                                        @Override
                                        public void success(Response response, Response response2) {



                                            Logger.d("dd", response.toString());
                                            Intent i=new Intent(getApplicationContext(),SendActivity.class);
                                            startActivity(i);


                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    });


                                }

                                @Override
                                public void failure(RetrofitError error) {

                                    Logger.d("error", error.getMessage());

                                }
                            });

                        } catch (UnsupportedEncodingException e) {

                        }

                    } catch (JSONException e) {

                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
                }





        }});


    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("TAG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
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
