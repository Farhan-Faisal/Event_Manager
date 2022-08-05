package com.example.b07_project;

import java.util.HashSet;

public class eventModel {
    public String name;
    public String date;
    public String venue;
    public String time;
    public String maxParticipants;

    private int participantsCount;
    long noParticipants;
    boolean spaceAvailable;

    public HashSet<String> participants;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }
    public String getSpace() {
        return "Space Available";
    }
    public String getMaxParticipants() {
        return String.valueOf(participantsCount);
    }


    public eventModel(){}

    public eventModel(String name, String date, String venue, String maxParticipants, String time){
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.time = time;
        this.maxParticipants = maxParticipants;
        noParticipants = 0;
        participantsCount = 0;

//        if (participantsCount == Integer.valueOf(maxParticipants)){
//            spaceAvailable = false;
//        }
//        else{
//            spaceAvailable = true;
//        }
    }

//    public void add_participant(String participant){
//        noParticipants = noParticipants + 1;
//        participants.add(participant);
//
//        if (Integer.valueOf(maxParticipants) == participantsCount){
//            spaceAvailable = false;
//        }
//        else{
//            spaceAvailable = true;
//        }
//        return;
//    }
//
//    public void remove_participant(String participant){
//        if(participantsCount == 0){
//            return;
//        }
//        if(participants.contains(participant) == false){
//            return;
//        }
//        participantsCount = participantsCount - 1;
//        participants.remove(participant);
//        return;
//    }

    @Override
    public boolean equals(Object o){
        boolean result = false;

        if (o == null){
            result =  false;
        }
        else if (o instanceof eventModel == false ){
            result =  false;
        }
        else {
            eventModel t = (eventModel) o;
            if ((this.name).compareTo(t.name) == 0 && (this.venue).compareTo(t.venue) == 0) {
                if ((this.date).compareTo(t.date) == 0 && (this.time).compareTo(t.time) == 0) {
                    result = true;
                }
            }
        }
        return result;
    }
}
