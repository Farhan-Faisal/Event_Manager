package com.example.b07_project;

import java.util.ArrayList;
import java.util.HashMap;

public class user {
    public String username;
    public String password;
    public HashMap<String, eventModel> eventsJoined;

    public user(){}

    public user(String name, String code){
        this.username = name;
        this.password = code;
        eventsJoined = new HashMap<String, eventModel>();
    }

    public boolean nameExists(HashMap<String, String> map){
        boolean exists = false;
        if (map.containsKey(username) == true) {
            exists = true;
        }
        return exists;
    }

    public boolean isValid(HashMap<String, String> map){
        boolean isValid = false;
        if (this.nameExists(map) == true) {
            if (map.get(username).equals(password) == true){
                isValid = true;
            }
        }
        return isValid;
    }
}
