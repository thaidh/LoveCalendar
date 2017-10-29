package com.thaidh.lovecalendar;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;

/**
 * Created by thaidh on 10/29/17.
 */

public class MainApplication extends Application {

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        FirebaseApp.initializeApp(getApplicationContext());
//    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
