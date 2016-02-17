package com.slavafleer.gson;

/**
 * Created by Slava on 17/02/2016.
 */
public class JsonTest {

    private String status;

    public JsonTest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status: " + status;
    }
}
