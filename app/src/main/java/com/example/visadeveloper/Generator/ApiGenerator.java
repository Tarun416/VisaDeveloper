package com.example.visadeveloper.Generator;

import android.util.Log;

import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.TlsVersion;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rahul on 09/03/16.
 */
public class ApiGenerator {

    private static String KEY_STORE_PATH = "/Users/rahul/Downloads";
    private static String KEY_STORE_PASSWORD = "abcd";
   static SSLContext sslContext;
   static TrustManagerFactory trustManagerFactory;
    static KeyManagerFactory keyManagerFactory;
    static KeyStore ks;


    public static String BASE_URL = "https://sandbox.api.visa.com/visadirect/";

    // No need to instantiate this class.
    private ApiGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();


        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/json");
                request.addHeader("Authorization", getBasicAuthHeader("44OXX2XY9UPKWU35RI6V21m4iFR9JznUk4GACR91spb4iiclE", "8N991FqhI8UiY24v11BZ1w2MffDC3NV8bY0Q2bw"));

            }
        };
        OkHttpClient okHttpClient = new OkHttpClient();

      //  OkHttpClient client = new OkHttpClient();
        KeyStore keyStore = readKeyStore(); //your method to obtain KeyStore

        try {
             sslContext = SSLContext.getInstance("SSL");


             trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }

        catch(NoSuchAlgorithmException e)
        {

        }

        try {
            trustManagerFactory.init(keyStore);
        }
        catch(KeyStoreException e)
        {

        }

        try {
           keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        }
        catch(NoSuchAlgorithmException e)
        {

        }
        try {
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
        }
        catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e)
        {

        }
        try {
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        }
        catch(KeyManagementException e)
        {

        }
        okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());

                okHttpClient.setConnectionSpecs(Collections.singletonList(spec));
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.d("Retro", msg);
                    }
                })
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

    private static KeyStore readKeyStore() {

        try {

             ks = KeyStore.getInstance(".p12");
        }
        catch (KeyStoreException e)
        {

        }

        try {
            ks.load(  new FileInputStream(KEY_STORE_PATH),KEY_STORE_PASSWORD.toCharArray());
        }
        catch (  NoSuchAlgorithmException | IOException | java.security.cert.CertificateException e)
        {

        }

        return ks;




    }

    private static String getBasicAuthHeader(String s, String s1) {




        return "Basic " + base64Encode(s + ":" + s1);
    }

    private static String base64Encode(String s) {
        byte[] encodedBytes = Base64.encodeBase64(s.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    public static <S> S createRevocableService(Class<S> serviceClass) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/json");
                request.addHeader("Authorization", getBasicAuthHeader("44OXX2XY9UPKWU35RI6V21m4iFR9JznUk4GACR91spb4iiclE", "8N991FqhI8UiY24v11BZ1w2MffDC3NV8bY0Q2bw"));
            }
        };


        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.d("Retro", msg);
                    }
                })
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

}

