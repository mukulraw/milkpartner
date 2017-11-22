package com.youthlive.milkpartner;

import com.youthlive.milkpartner.Book1POJO.BookBean;
import com.youthlive.milkpartner.Book2POJO.Book2Bean;
import com.youthlive.milkpartner.BookingPOJO.BookingBean;
import com.youthlive.milkpartner.CityListPOJO.CityBean;
import com.youthlive.milkpartner.loginPOJO.loginBean;

import okhttp3.MultipartBody;
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

    @Multipart
    @POST("milk-partner/api/user_login.php")
    Call<loginBean> login(
            @Part("email") String email,
            @Part("password") String pass
    );





    @Multipart
    @POST("milk-partner/api/add_booking.php")
    Call<BookBean> book(
            @Part("user_id") String id,
            @Part("state") String state,
            @Part("district") String dis,
            @Part("latitude") String lati,
            @Part("longitude") String longi,
            @Part("area") String area,
            @Part("location") String location,
            @Part MultipartBody.Part file,
            @Part("sample") String sam
    );



    @Multipart
    @POST("milk-partner/api/booking_list.php")
    Call<BookingBean> booking
            (@Part("user_id") String id);





    @Multipart
    @POST("milk-partner/api/update_booking.php")
    Call<Book2Bean> book2(
            @Part("user_id") String id,
            @Part("booking_id") String state,
            @Part("adultration") String dis,
            @Part("pesticides") String lati,
            @Part("antibiotics") String longi,
            @Part("aflatoxin") String area
    );




}
