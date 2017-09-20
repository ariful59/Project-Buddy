package com.azizulhakim.todotogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.azizulhakim.todotogether.FirebaseUtil;
import com.azizulhakim.todotogether.R;
import com.azizulhakim.todotogether.TaskDetailsPage;
import com.azizulhakim.todotogether.models.Task;
import com.azizulhakim.todotogether.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;


public abstract class TaskFragmentInterface extends Fragment {

    private static final String TAG = "TaskFragmentInterface";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Task, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private String mpostKey;

    public TaskFragmentInterface() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);

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
        mAdapter = new FirebaseRecyclerAdapter<Task, PostViewHolder>(Task.class, R.layout.item_post,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Task model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                mpostKey = postKey;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch TaskDetailsPage
                        Intent intent = new Intent(getActivity(), TaskDetailsPage.class);
                        intent.putExtra(TaskDetailsPage.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_action_toggle_star);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_action_toggle_star_outline);
                }

                // Bind Task to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        String groupkey = FirebaseUtil.getCurrentGroupID();
                        DatabaseReference userPostRef = mDatabase.child("group-posts").child(groupkey).child(postRef.getKey());
                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }



    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference postRef) {

        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Task p = mutableData.getValue(Task.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {

                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]

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
