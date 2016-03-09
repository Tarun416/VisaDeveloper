package com.example.visadeveloper.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by rahul on 09/03/16.
 */
public interface MvisaApi  {


    @POST("/mvisa/v1/cashinpushpayments")
    void debit(@Body TypedInput in, Callback<Response> responseCallback);
}
