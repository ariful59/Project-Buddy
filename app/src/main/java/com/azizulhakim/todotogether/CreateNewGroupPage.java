package com.azizulhakim.todotogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.azizulhakim.todotogether.models.Group;
import com.azizulhakim.todotogether.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateNewGroupPage extends InterfaceActivity{

    private static final String TAG = "CreateNewGroupPage";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page__new_group);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = (EditText) findViewById(R.id.field_title);
        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_group);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGruop();
            }
        });
    }
    public void submitGruop() {
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

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Creating...", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(CreateNewGroupPage.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            createNewGroup(userId, user.username, title, body);
                        }


                        // Finish this Activity, back to the stream
                        startActivity( new Intent(CreateNewGroupPage.this, HomePage.class));
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
    private void createNewGroup(String userId, String username, String title, String body) {

        DatabaseReference groupRef = mDatabase.child("groups").push();
        String groupID = groupRef.getKey();

        Group group = new Group(title, body);

        groupRef.setValue( group );
        mDatabase.child("user-groups").child(userId).child(groupID).setValue(group);
/*
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/groups/" + groupkey, groupDetails);
        childUpdates.put("/user-groups/" + userId + "/" + groupkey, groupDetails);
        childUpdates.put("/group-users/" + groupkey + "/" + userId, userId);

        mDatabase.updateChildren(childUpdates);

        */
    }
    // [END write_fan_out]
}
