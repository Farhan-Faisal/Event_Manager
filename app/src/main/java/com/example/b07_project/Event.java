package com.example.b07_project;

import java.util.HashSet;

public class Event {
    public String date;
    public String endTime;
    public String maxParticipants;
    public String name;
    double noParticipants;
    public String space;
    public String time;
    public String venue;

    public Event(){}

    public Event(String date, String name, String endTime, String venue, String maxParticipants, String time, String space, double noParticipants){
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.time = time;
        this.maxParticipants = maxParticipants;
        this.noParticipants = noParticipants;
        this.endTime = endTime;
        this.space = space;

//        if (participantsCount == Integer.valueOf(maxParticipants)){
//            spaceAvailable = false;
//        }
//        else{
//            spaceAvailable = true;
//        }
    }

    @Override
    public boolean equals(Object o){
        boolean result = false;

        if (o == null){
            return false;
        }
        else if (o instanceof eventModel == false ){
            return false;
        }
        else {
            Event t = (Event) o;
            if ((this.name).compareTo(t.name) == 0 && (this.venue).compareTo(t.venue) == 0) {
                if ((this.date).compareTo(t.date) == 0 && (this.time).compareTo(t.time) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
