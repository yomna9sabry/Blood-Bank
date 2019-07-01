package com.example.jokyom.bloodbank.data.rest;

import com.example.jokyom.bloodbank.data.model.governments.Governments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("governorates")
    Call<Governments>getGovernorates();

  /*@GET("cities")
    Call<>getCities(@Query("governorate_id") int governorate_id);
    */



}
