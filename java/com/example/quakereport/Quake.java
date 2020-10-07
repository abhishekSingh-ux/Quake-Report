package com.example.quakereport;

public class Quake {
    private double mMagnitude;
    private String mPlace;
    private long mDate;
    private String mUrl;

    public Quake(double mMagnitude, String mPlace, long mDate, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mPlace = mPlace;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmDate() {
        return mDate;
    }

    public String getmUrl() { return mUrl; }
}
