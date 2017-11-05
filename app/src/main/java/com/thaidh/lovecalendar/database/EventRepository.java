package com.thaidh.lovecalendar.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by thaidh on 11/5/17.
 */

public class EventRepository {

    public static Query mEventQuery = FirebaseDatabase.getInstance().getReference().child("events");

    public static void updateEventQuery(String uid) {
        mEventQuery = FirebaseDatabase.getInstance().getReference().child("events").child(uid);
    }
}
