package com.slavafleer.nearpois;

import android.app.Activity;

/**
 * Data Class for Quick Search Recycler View.
 */
public class QuickSearch {

    private String imageName;
    private int imageId;
    private String type;

    public QuickSearch(Activity activity, String type, String imageName) {
        this.imageName = imageName;
        this.type = type;

        imageId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
