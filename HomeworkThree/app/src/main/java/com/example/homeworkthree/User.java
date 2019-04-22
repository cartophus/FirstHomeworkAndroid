package com.example.homeworkthree;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    String userId;
    String name;
    String username;
    String email;
    Address address;
    String phone;
    String website;
    Company company;

    User(){}

    User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    User(JSONObject jsonObject) throws JSONException{
        this.userId = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
    }

    User(String userId, String name, String username, String email, Address address, String phone, String website, Company company)
    {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject userJSON = new JSONObject();

        userJSON.put("userId", this.userId);
        userJSON.put("name", this.name);

        return userJSON;
    }
}
