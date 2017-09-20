package com.azizulhakim.todotogether;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azizulhakim.todotogether.models.Group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AZIZUL on 11/6/2016.
 */
public class AddUserPage extends InterfaceActivity {
    private static final String TAG = "AddUserPage";
    private static final String REQUIRED = "Required";

    private String mgroupkey = "groupkey";
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private DatabaseReference mUserRef;
    // [END declare_database_ref]

    private EditText mMailField;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page__add_user);

        mgroupkey = FirebaseUtil.getCurrentGroupID();

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mMailField = (EditText) findViewById(R.id.user_mail);
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    String makeUserID(String emailname){
        String key = emailname + emailname;
        key = key.replace('.', ',');
        return key;
    }

    private void addUser() {
        final String mail = mMailField.getText().toString();

        // Mail is required
        if (TextUtils.isEmpty(mail)) {
            mMailField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        String emailname = usernameFromEmail(mail);
        final String userId = makeUserID(emailname);


        // [ ADD USE START ]

        mUserRef = FirebaseUtil.getUserReference();
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userId)){
                    Toaster("Adding...");
                    addUserToGroup(userId, mgroupkey);
                }
                else {
                    Toaster("User Doesn't exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        finish();

    }

    void addUserToGroup(final String userId,final String groupID){

        DatabaseReference ref = FirebaseUtil.getGroupReference();
        ref = ref.child(groupID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group group= dataSnapshot.getValue( Group.class );
                mDatabase.child("user-groups").child(userId).child(groupID).setValue(group);
                Toaster("Add Succesful!!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toaster("Failed to add!");
            }
        });


    }

    private void setEditingEnabled(boolean enabled) {
        mMailField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]

    // [END write_fan_out]
}
