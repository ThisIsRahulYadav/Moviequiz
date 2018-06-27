package com.rauniyargames.moviequiz.helperclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

/**
 * Created by lenovo on 3/28/2018.
 */

public class ReferenceWrapper {
    Activity activity;

    private static ReferenceWrapper referenceWrapper;
    private ReferenceWrapper(Activity activity){
        this.activity=activity;
    }
    public static ReferenceWrapper getReferenceWrapper(Activity activity){
        if(referenceWrapper==null){
          referenceWrapper = new ReferenceWrapper(activity);
        }  return  referenceWrapper;
    }

    public void setSharedPreference(Activity activity,String key,int value){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public int getSharedPreference(Activity activity,String key,int defaultValue){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(activity.getPackageName(),Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,defaultValue);
    }

    public void stopSound(Activity activity) {


        Intent svc = new Intent(activity, BackgroundSoundService.class);
        activity.stopService(svc);
    }
    public void startSound(Activity activity) {

        Intent svc = new Intent(activity, BackgroundSoundService.class);
        activity.startService(svc);
    }


}
