package com.mojafarin.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;

import com.mojafarin.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;



/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class AppController extends Application implements Thread.UncaughtExceptionHandler {

    public static LayoutInflater inflater;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onTerminate() {

        super.onTerminate();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.exit(1);
    }


}