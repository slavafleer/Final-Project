package com.slavafleer.nearpois;

/**
 * Data Class
 */
public class Poi {

    private long id;
    private String name;
    private String address;
    private String vicinity;
    private String distanceText;
    private int distanceValue;
    private String place_id;
    private double latitude;
    private double longitude;
    private String photoReference;
    private String iconUrl;
    private String isOpen;
    private double rating;
    private String walkingDurationText;
    private long walkingDurationValue;
    private String drivingDurationText;
    private long drivingDurationValue;

    public Poi() {
    }

    public Poi(String name) {
        this.name = name;
    }

    public Poi(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Poi(String name, String vicinity, String place_id, double latitude,
               double longitude, String photoReference, String iconUrl, String isOpen,
               double rating) {
        this.name = name;
        this.vicinity = vicinity;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
        this.iconUrl = iconUrl;
        this.isOpen = isOpen;
        this.rating = rating;
    }

    public Poi(long id, String name, String address, String vicinity, String distanceText,
               int distanceValue, String place_id, double latitude, double longitude,
               String photoReference, String iconUrl, String isOpen, double rating,
               String walkingDurationText, long walkingDurationValue, String drivingDurationText,
               long drivingDurationValue) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.vicinity = vicinity;
        this.distanceText = distanceText;
        this.distanceValue = distanceValue;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
        this.iconUrl = iconUrl;
        this.isOpen = isOpen;
        this.rating = rating;
        this.walkingDurationText = walkingDurationText;
        this.walkingDurationValue = walkingDurationValue;
        this.drivingDurationText = drivingDurationText;
        this.drivingDurationValue = drivingDurationValue;
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

    public int getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(int distanceValue) {
        if (distanceValue >= 0) {
            this.distanceValue = distanceValue;
        }
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public String getWalkingDurationText() {
        return walkingDurationText;
    }

    public void setWalkingDurationText(String walkingDurationText) {
        this.walkingDurationText = walkingDurationText;
    }

    public String getDrivingDurationText() {
        return drivingDurationText;
    }

    public void setDrivingDurationText(String drivingDurationText) {
        this.drivingDurationText = drivingDurationText;
    }

    public long getWalkingDurationValue() {
        return walkingDurationValue;
    }

    public void setWalkingDurationValue(long walkingDurationValue) {
        this.walkingDurationValue = walkingDurationValue;
    }

    public long getDrivingDurationValue() {
        return drivingDurationValue;
    }

    public void setDrivingDurationValue(long drivingDurationValue) {
        this.drivingDurationValue = drivingDurationValue;
    }
}
