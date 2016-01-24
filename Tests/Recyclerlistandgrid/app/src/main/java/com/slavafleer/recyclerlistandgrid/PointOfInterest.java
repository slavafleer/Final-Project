package com.slavafleer.recyclerlistandgrid;

import android.net.Uri;

// Data Class
public class PointOfInterest {

    private long id;
    private String name;
    private String address;
    private double distance;
    private Uri pictureUri;

    public PointOfInterest() {
    }

    public PointOfInterest(String name, String address, double distance, Uri pictureUri) {

        setName(name);
        setAddress(address);
        setDistance(distance);
        setPictureUri(pictureUri);
    }

    public PointOfInterest(long id, String name, String address, double distance, Uri pictureUri) {

        this(name, address, distance, pictureUri);
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id >= 0) {
            this.id = id;
        }
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if(distance >= 0) {
            this.distance = distance;
        }
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        //TODO: Need to decide if to check for URL or URI or change to String.
//        if(Patterns.)
        this.pictureUri = pictureUri;
    }

    @Override
    public String toString() {

        return "ID:" + id + ", Name: " + name + ", Address: " + address + ", Distance: " + distance;
    }
}
