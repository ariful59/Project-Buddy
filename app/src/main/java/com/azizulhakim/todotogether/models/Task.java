package com.azizulhakim.todotogether.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Task {

    public String uid;
    public String author;
    public String title;
    public String body;
    public Date duedate;
    public Date createdate;
    public String status;
    public String handler;
    public String addedby;
    public String doingby;
    public String doneby;

    /*
    1 = TO-DO
    2 = DOING
    3 = DONE
     */

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Task(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.addedby=author;
        this.title = title;
        this.body = body;
    }

    public void addDoingBy(String name){
        doingby = name;
    }

    public void addDoneBy(String name){
        doneby = name;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
