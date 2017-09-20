package com.azizulhakim.todotogether.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AZIZUL on 10/26/2016.
 */
@IgnoreExtraProperties
public class Group {
    public String uid;
    public String groupid;
    public String groupname;
    public String about;


    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Group() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Group(String name, String about) {
        this.groupname = name;
        this.about = about;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("groupid", groupid);
        result.put("groupname", groupname);
        result.put("about", about);
        result.put("starCount", starCount);
        result.put("stars", stars);
        return result;
    }
    // [END post_to_map]

}
