package com.mhacks11app.mhacks11app;

import com.google.firebase.firestore.GeoPoint;

public class LocationInfo {

    private GeoPoint location;
    private String title;
    private String ToD1;
    private String ToD2;
    private String activityLevel1;
    private String activityLevel2;
    private String style1;
    private String style2;
    private String style3;
    private String style4;
    private double latitude;
    private double longitude;

    public LocationInfo() {}

    public LocationInfo(String title, String ToD1, String ToD2, String activityLevel1,
                 String activityLevel2, GeoPoint location, String style1, String style2,
                 String style3, String style4 ) {
        this.title = title;
        this.ToD1 = ToD1;
        this.ToD2 = ToD2;
        this.activityLevel1 = activityLevel1;
        this.activityLevel2 = activityLevel2;
        this.location = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }

    public String getTitle() {
        return title;
    }

    public String getToD1() {
        return ToD1;
    }

    public String getToD2() {
        return ToD2;
    }

    public String getActivityLevel1() {
        return activityLevel1;
    }

    public String getActivityLevel2() {
        return activityLevel2;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStyle1() {
        return style1;
    }

    public String getStyle2() {
        return style2;
    }

    public String getStyle3() {
        return style3;
    }

    public String getStyle4() {
        return style4;
    }
}
