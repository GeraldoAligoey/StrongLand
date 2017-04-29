package me.eljae.strongland.model;

/**
 * Created by gma on 30/04/2017.
 */

public class CustomData {
    private String lat;
    private String lng;
    private String address;
    private String country;
    private String timestamp;
    private String trigger;
    private String scale;
    private String type;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomData(String lat, String lng, String address, String country, String timestamp, String trigger, String scale, String type) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.country = country;
        this.timestamp = timestamp;
        this.trigger = trigger;
        this.scale = scale;
        this.type = type;
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
