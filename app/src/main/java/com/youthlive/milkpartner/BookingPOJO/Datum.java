package com.youthlive.milkpartner.BookingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 11/21/2017.
 */

public class Datum {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sample")
    @Expose
    private String sample;
    @SerializedName("adultration")
    @Expose
    private String adultration;
    @SerializedName("pesticides")
    @Expose
    private String pesticides;
    @SerializedName("antibiotics")
    @Expose
    private String antibiotics;
    @SerializedName("aflatoxin")
    @Expose
    private String aflatoxin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getAdultration() {
        return adultration;
    }

    public void setAdultration(String adultration) {
        this.adultration = adultration;
    }

    public String getPesticides() {
        return pesticides;
    }

    public void setPesticides(String pesticides) {
        this.pesticides = pesticides;
    }

    public String getAntibiotics() {
        return antibiotics;
    }

    public void setAntibiotics(String antibiotics) {
        this.antibiotics = antibiotics;
    }

    public String getAflatoxin() {
        return aflatoxin;
    }

    public void setAflatoxin(String aflatoxin) {
        this.aflatoxin = aflatoxin;
    }
}
