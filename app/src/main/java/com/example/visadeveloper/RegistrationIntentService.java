package com.example.visadeveloper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hp on 14-03-2016.
 */
public class RegistrationIntentService extends IntentService {
    String api_key="AIzaSyC_IPD6o5iSyz90RtS_O7RdvlJRgpL1iRQ";
    String devicetoken="dnlya3UemDk:APA91bFiZ-Q6BiP7hUNcrL2LAAfAZYPIpRQdx-mParpMH2_8VIZHJUa0QFYWptneIZbIeSOJv5KDQWdQWkdSPAL6MnjohLvhxt9S1I0dXjwlo7chxuC61Ev4imUL8HnXQ5xJxbyswLP3";
 String merchanttoken="d4PrgjELRcw:APA91bG4Jf6Tg-DgonZLiU7618NDgq3Orup-Oe1rSV7kTxHqvcyuZj_rfz3vxLUX44eLCYucWg3VU8r7WMnOy1CPF8aqTI5CR8qS-fNfPX2ihMOHlTm6Y_xB7eFuxHJtmvH6O2pexd43";
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String fullname=  intent.getStringExtra("fullname");
        String accountno=  intent.getStringExtra("accountno");



        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);

            SharedPreferences prefs = getSharedPreferences("UserDetails",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("regId", token);
            editor.putString("fullname", fullname);
            editor.putString("accountno", accountno);

            editor.commit();

            sendRegistrationToServer(token);

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);

        }

    }


    private void sendRegistrationToServer(String token) {


        try
        {

            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
         try {
             jData.put("message", "{ \"data\": {\"score\": \"5x1\",\"time\": \"15:10\"},\"to\" : \"" + devicetoken + "\"}");

             jGcmData.put("to",devicetoken);
         }
         catch(JSONException e)
         {

         }


            try {
                         jGcmData.put("data", jData);
                     }
                     catch(JSONException e)
                     {

                     }


            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" +api_key);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());
            outputStream.close();

            String text = getText(new InputStreamReader(conn.getInputStream()));
            System.out.println(text);

            InputStream inputStream = conn.getInputStream();
        //  String b=  getText(inputStream);
           // String resp = IOUtils.toString(inputStream);
          //  System.out.println(resp);
        } catch (IOException e) {
            System.out.println("Unable to send GCM message. " + e);
        }



    }

    public static String getText(InputStreamReader in) throws IOException {
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(in);
        String read;
        while((read=br.readLine()) != null) {
            sb.append(read);
        }
        br.close();
        return sb.toString();
    }


    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }


}


