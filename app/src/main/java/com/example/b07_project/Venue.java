package com.example.b07_project;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    public String close_time;
    public String location;
    public String open_time;
    public String venueName;
    public String sports;

    public String getSports() {
        return sports;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenue(String venueName) {
        this.venueName = venueName;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public String getClose_time() {
        return close_time;
    }

    public void setEnd_time(String end_time) {
        this.close_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_time() {
        return open_time;
    }

    public void setStart_time(String start_time) {
        this.open_time = start_time;
    }

    public Venue(){
    }
    public Venue(String venue, String end_time, String location, String start_time, String sports){
        this.venueName = venue;
        this.close_time = end_time;
        this.location = location;
        this.open_time = start_time;
        this.sports = sports;
    }
}
