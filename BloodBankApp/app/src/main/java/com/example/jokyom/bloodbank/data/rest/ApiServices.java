package com.example.jokyom.bloodbank.data.rest;

import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;

import com.example.jokyom.bloodbank.data.model.governments.Governments;
import com.example.jokyom.bloodbank.data.model.login.Login;
import com.example.jokyom.bloodbank.data.model.newPassword.NewPassword;
import com.example.jokyom.bloodbank.data.model.register.Register;
import com.example.jokyom.bloodbank.data.model.resetPassword.ResetPassword;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("governorates")
    Call<Governments>getGovernorates();

   /* @GET("cities")
    Call<>getCity(@Query("governorate_id") int governorate_id);
*/
    @POST("login")
    @FormUrlEncoded
    Call<Login>UserLogin(@Field("phone") String phone,
                     @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<Register>register(@Field("name") String name,
                           @Field("email") String email,
                           @Field("birth_date") String birth_date,
                           @Field("city_id") String city_id,
                           @Field("phone") String phone,
                           @Field("donation_last_date") String  donation_last_date,
                           @Field("password") String password,
                           @Field("password_confirmation") String password_confirmation,
                           @Field("blood_type_id") String blood_type_id);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword>resetPassword(@Field("reset-password")  String resetPassword );

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword(@Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation,
                                  @Field("pin_code") String pin_code,
                                  @Field("phone") String phone);

}

