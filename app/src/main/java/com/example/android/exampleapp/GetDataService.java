package com.example.android.exampleapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("logs/category1/")
    Call<Data> getAllDataOne();
    @GET("logs/category2/")
    Call<Data> getAllDataTwo();
    @GET("logs/category3/")
    Call<Data> getAllDataThree();
}
