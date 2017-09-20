package com.azizulhakim.todotogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azizulhakim.todotogether.models.Task;
import com.azizulhakim.todotogether.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.azizulhakim.todotogether.CalendarPage;
import com.azizulhakim.todotogether.TimeSetPage;

import static com.azizulhakim.todotogether.CalendarPage.result_date;
import static com.azizulhakim.todotogether.TimeSetPage.result_time;



//import com.google.firebase.quickstart.database.models.Task;
//import com.google.firebase.quickstart.database.models.User;

public class CreateNewPostPage extends InterfaceActivity{

    private static final String TAG = "CreateNewPostPage";
    private static final String REQUIRED = "Required";


    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public static final String EXTRA_POST_KEY = "post_key";
    private String groupKey;
    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    private Button addDate,addTime;
    private TextView datetext,timetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page__new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        groupKey = FirebaseUtil.getCurrentGroupID();

        mTitleField = (EditText) findViewById(R.id.field_title);
        mBodyField = (EditText) findViewById(R.id.field_body);
        /*addTime=(Button)findViewById(R.id.deadlinetime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(CreateNewPostPage.this, TimeSetPage.class));

            }
        });
        addDate=(Button)findViewById(R.id.deadlinetext);
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(CreateNewPostPage.this, CalendarPage.class));

            }
        });
        */
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    public void submitPost(){
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }
       /* timetext=(TextView)findViewById(R.id.timetext);
        timetext.setText(result_time);
        datetext=(TextView)findViewById(R.id.datetext);
        datetext.setText(result_date);*/
        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(CreateNewPostPage.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new task at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        DatabaseReference postRef = mDatabase.child("posts").push();
        String postKey = postRef.getKey();


        Task task = new Task(userId, username, title, body);
        task.setStatus( "1" );

        postRef.setValue(task);
        mDatabase.child("group-posts").child(groupKey).child(postKey).setValue(task);

        /*
        String key = mDatabase.child("posts").push().getKey();
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/group-posts/" + groupKey + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
        */
    }
    // [END write_fan_out]
}
