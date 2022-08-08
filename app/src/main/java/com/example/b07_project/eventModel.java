package com.example.b07_project;

import java.util.HashMap;
import java.util.HashSet;

public class eventModel {
    public String name;
    public String date;
    public String venue;
    public String startTime;
    public String endTime;
    public String maxParticipants;

    public String noParticipants;
    public String space;

    private int participantsCount;


    public eventModel(){}

    public eventModel(String name, String date, String venue, String maxParticipants, String noParticipants, String startTime,
                      String endTime){
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxParticipants = maxParticipants;
        this.noParticipants = noParticipants;
        participantsCount = Integer.valueOf(noParticipants);

        if (participantsCount == Integer.valueOf(maxParticipants)){
            space = "Event Full";
        }
        else{
            space = "Space Available";
        }
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public String getSpace() {
        return space;
    }
    public String getMaxParticipants() {
        return maxParticipants;
    }

    public String getNoParticipants() {
        return noParticipants;
    }

    public void add_participant(){
        if (noParticipants.compareTo(maxParticipants) == 0){
            return;
        }
        else{
            participantsCount++;
            noParticipants = String.valueOf(participantsCount);
            if (noParticipants.compareTo(maxParticipants) == 0){
                space = "Event Full";
            }
        }
    }

    public HashMap<String, String> convertToHashMap(){
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName());
        result.put("date", getDate());
        result.put("venue", getVenue());
        result.put("startTime", getStartTime());
        result.put("endTime", getEndTime());
        result.put("maxParticipants", getMaxParticipants());

        result.put("noParticipants", getNoParticipants());
        result.put("space", getSpace());
        return result;
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
            eventModel t = (eventModel) o;
            if ((this.name).compareTo(t.name) == 0 && (this.venue).compareTo(t.venue) == 0) {
                if ((this.date).compareTo(t.date) == 0 && (this.startTime).compareTo(t.startTime) == 0
                && (this.endTime).compareTo(t.endTime) == 0){
                    return true;
                }
            }
        }
        return false;
    }
}
