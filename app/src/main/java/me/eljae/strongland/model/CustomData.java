package me.eljae.strongland.model;

/**
 * Created by gma on 30/04/2017.
 */

public class CustomData {
    private String lat;
    private String lng;
    private String address;
    private String country;

    public CustomData(String lat, String lng, String address, String country) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.country = country;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
