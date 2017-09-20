package com.azizulhakim.todotogether.fragment;

import com.azizulhakim.todotogether.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class TaskToDoFragment extends TaskFragmentInterface {

    public TaskToDoFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        String groupkey = FirebaseUtil.getCurrentGroupID();
        return databaseReference.child("group-posts")
                .child(groupkey).orderByChild("status").equalTo("1");
    }
}
