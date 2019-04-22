package com.example.homeworkthree;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ToDoItem {
    String userId;
    String id;
    String title;
    Boolean completed;

    public ToDoItem(){}

    public ToDoItem(JSONObject jsonObject) throws JSONException{
        this.userId = jsonObject.getString("userId");
        this.id = jsonObject.getString("id");
        this.title = jsonObject.getString("title");
        this.completed = jsonObject.getBoolean("completed");
    }

    public JSONObject toJson() throws JSONException{
        JSONObject toDoItemJSON = new JSONObject();

        toDoItemJSON.put("userId", this.userId);
        toDoItemJSON.put("id", this.id);
        toDoItemJSON.put("title", this.title);
        toDoItemJSON.put("completed", this.completed);

        return toDoItemJSON;
    }
}