package com.youthlive.milkpartner;

import com.youthlive.milkpartner.CityListPOJO.CityBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by tvs on 11/6/2017.
 */

public interface Allapi {

    @GET("eduschool/eduschool_app/all_state.php")
    Call<StateBean> s();



    @Multipart
    @POST("eduschool/eduschool_app/all_city.php")
    Call<CityBean> city
            (@Part("state_id") String st);
}