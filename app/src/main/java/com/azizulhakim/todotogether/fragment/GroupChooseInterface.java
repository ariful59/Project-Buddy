package com.azizulhakim.todotogether.fragment;

/**
 * Created by AZIZUL on 10/30/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azizulhakim.todotogether.FirebaseUtil;
import com.azizulhakim.todotogether.R;
import com.azizulhakim.todotogether.TaskListPage;
import com.azizulhakim.todotogether.models.Group;
import com.azizulhakim.todotogether.viewholder.GroupViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;


public abstract class GroupChooseInterface extends Fragment {

    private static final String TAG = "GroupFragmentInterface";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Group, GroupViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public GroupChooseInterface() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_groups, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Group, GroupViewHolder>(Group.class, R.layout.item_group,
                GroupViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final GroupViewHolder viewHolder, final Group model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch GroupDetailsPage
                        //Intent intent = new Intent(getActivity(), CreateNewPostPage.class);
                        //intent.putExtra(CreateNewPostPage.EXTRA_POST_KEY, postKey);
                        Intent intent = new Intent(getActivity(), TaskListPage.class);
                        intent.putExtra(TaskListPage.GROUP_KEY, postKey);
                        FirebaseUtil.setCurrentGroup(postKey);
                        startActivity(intent);
                    }
                });


                // Bind Task to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseUtil.getMyUserID();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
