package com.example.test1.Services;

import com.example.test1.Model.DataResponse;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by abhay on 09/03/19.
 */
public interface RequestApi {

    @GET("media.json?print=pretty")
    Call <ArrayList<DataResponse>> data();


}
