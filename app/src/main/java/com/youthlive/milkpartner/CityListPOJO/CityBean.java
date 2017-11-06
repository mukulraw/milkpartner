package com.youthlive.milkpartner.CityListPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tvs on 11/6/2017.
 */

public class CityBean {

    @SerializedName("city_list")
    @Expose
    private List<CityList> cityList = null;

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

}
