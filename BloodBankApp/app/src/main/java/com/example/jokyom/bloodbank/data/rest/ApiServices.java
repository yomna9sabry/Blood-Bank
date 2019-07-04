package com.example.jokyom.bloodbank.data.rest;

import com.example.jokyom.bloodbank.data.model.governments.Governments;
import com.example.jokyom.bloodbank.data.model.user.Login;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("governorates")
    Call<Governments>getGovernorates();

    @POST("login")
    @FormUrlEncoded
    Call<Login>login();
}
