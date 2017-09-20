package com.azizulhakim.todotogether.fragment;

/**
 * Created by AZIZUL on 10/26/2016.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GroupMyFragment extends GroupChooseInterface {

    public GroupMyFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        return databaseReference.child("user-groups").child(myUserId);
        // [END my_top_posts_query]

    }
}
