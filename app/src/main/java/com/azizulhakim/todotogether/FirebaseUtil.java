package com.azizulhakim.todotogether;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AZIZUL on 11/6/2016.
 */
public class FirebaseUtil {

    public static String CurrentGroupID = null;
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public static String myUserID = null;
    public static String myname = "Azizul Hakim";
    public static String myemail = "azizulhakim.csedu@gmail.com";

    public static void setMyNameAndEmail(String name, String mail){
        myname = name;
        myemail = mail;
    }

    public static void setCurrentGroup(String GroupKey){
        CurrentGroupID = GroupKey;
    }

    public static String getCurrentGroupID(){
        return CurrentGroupID;
    }

    public static void setMyUserID(String id){
        myUserID = id;
    }

    public static String getMyUserID() {
        return myUserID;
    }

    public static String getMyName(){ return myname; }

    public static String getMyEmail(){ return myemail; }

    public static DatabaseReference getUserReference() {
        return mDatabase.child("users");
    }

    public static DatabaseReference getGroupReference() { return mDatabase.child("groups"); }


}
