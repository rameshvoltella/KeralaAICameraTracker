package com.ramzmania.aicammvd.boardcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity;
import com.ramzmania.aicammvd.utils.NotificationHelper;

import java.util.List;
import java.util.Set;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GeofenceBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "trigged"+intent, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: Intent action = " + intent.getAction());
        Bundle extras = intent.getExtras();
        String keyss="";
        if (extras != null) {
            Set<String> keys = extras.keySet(); // Retrieve all keys
            for (String key : keys) {
                // Print or use each key as needed
                Log.d("IntentKeys", "Key: " + key);
                keyss=keyss+"keyss"+key;
            }
        } else {
            // No extras found in the intent
        }
        Toast.makeText(context, "trigged"+intent, Toast.LENGTH_SHORT).show();

//        throw new UnsupportedOperationException("Not yet implemented");
        NotificationHelper notificationHelper=new NotificationHelper(context);
        Log.d(TAG, "onReceive: recived");
        GeofencingEvent geofencingEvent=GeofencingEvent.fromIntent(intent);
        if(geofencingEvent==null)
        {
            return;
        }

        if(geofencingEvent.hasError())
        {
            Log.d(TAG, "onReceive1: ERROR RECIVING GEOEVENT");
            return;
        }
        List<Geofence> geofence=geofencingEvent.getTriggeringGeofences();

      //  Location location=geofencingEvent.getTriggeringLocation();
        int transitiontype=geofencingEvent.getGeofenceTransition();
        switch (transitiontype)
        {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "entered", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("entered","", HomeActivity.class);
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "ROAMING", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("roaaming","",HomeActivity.class);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "EXITED", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("exit","",HomeActivity.class);
        }


    }
}