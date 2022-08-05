package com.example.b07_project;

import android.util.Log;

import java.util.HashMap;

public class admin {
    public String name;
    public String password;

    public admin(){}

    public admin(String name, String code){
        this.name = name;
        this.password = code;
    }

    public boolean validate(HashMap<String, String> map){
        boolean isValid = false;
        if (map.containsKey(name) == true) {
            if (map.get(name).equals(password)){
                isValid = true;
            }
        }
        return isValid;
    }
}
