package com.slavafleer.gson;

/**
 * Data Class
 */
public class Poi {

    private long id;
    private String name;
    private String address;
    private String vicinity;
    private double distance;
    private String place_id;
    private float latitude;
    private float longitude;
    private String photoReference;
    private String iconUrl;

    @Override
    public String toString() {
        return id + "\n" + name + "\n" + address + "\n" + vicinity + "\n" +
                distance + "\n" +place_id + "\n" +latitude + "\n" +longitude + "\n" +
                photoReference + "\n" +iconUrl;
    }

    public Poi() {
    }

    public Poi(String name) {
        this.name = name;
    }

    public Poi(String name, String vicinity, String place_id, float latitude,
               float longitude, String photoReference, String iconUrl) {
        this.name = name;
        this.vicinity = vicinity;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
        this.iconUrl = iconUrl;
    }

    public Poi(long id, String name, String address, String vicinity,
               double distance, String place_id, float latitude,
               float longitude, String photoReference, String iconUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.vicinity = vicinity;
        this.distance = distance;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
        this.iconUrl = iconUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if (distance >= 0) {
            this.distance = distance;
        }
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }


}
