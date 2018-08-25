package com.mojafarin.ui.service;


import ir.abrin.PushObject;
import ir.abrin.service.AbrinBaseIntentService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.util.Log;

import com.mojafarin.Modal.Notify;
import com.mojafarin.R;
import com.mojafarin.db.DatabaseHandler;
import com.mojafarin.ui.activity.MainActivity;

public class PushIntentService extends AbrinBaseIntentService {

    private static final String TAG = "Abrin errors";
    public static int NOTIFICATION_ID = 1;
    private WakeLock wakelock = null;

    public PushIntentService() {
        super();
    }

    /**
     * Method called on device registered
     * For compatibility
     **/
    @Override
    public void onRegistered(Context context, String registrationId) {
//    	AbrinCloudService.getInstance(this).addTag(1, "تست");

    }

    @Override
    public void onReady(Context context) {
        System.err.println("Abrin is ready");
    }

    /**
     * Method called on device unregistred
     * For compatibility
     */
    @Override
    public void onUnregistered(Context context) {
        System.err.println("Unregistered");
    }

    /**
     * Method called on Receiving a new message
     * For compatibility
     */
    @Override
    public void onMessage(Context context, Intent intent) {
        try {

            acquireWakeLock();
//    	String message = intent.getExtras().getString(GcmIntentService.MESSSAGE);
            Bundle b = intent.getExtras().getBundle("notify");
            PushObject message = b.getParcelable("notify");
            Log.d("Push Data: ", message.getBody() + " " + message.getTitle());
            //save to data base message
            new DatabaseHandler(this).addNotify(new Notify(message.getMessageUID(),message.getTitle(),message.getBody(),message.getPicture(),message.getIcon(),0));
            sendNotification(message);
            System.err.println("data in client " + message.getBody());
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        releaseWakeLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
//        releaseWakeLock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTagged(Context context, Integer tagId) {
        System.err.println("TAGGED: " + tagId);
    }

    @Override
    public void onRemovedTag(Context context, Integer tagId) {
        System.err.println("TAG Removed: " + tagId);
    }

    @Override
    public void onAliasChanged(Context context, String alias) {
        System.err.println("Alias Changed: " + alias);
    }

    /**
     * Method called on receiving a deleted message
     * For compatibility
     */
    @Override
    public void onDeletedMessages(Context context, int total) {

    }

    /**
     * Acquires a partial wake lock for this client
     */
    private void acquireWakeLock() {
        if (wakelock == null) {
            PowerManager pm = (PowerManager) getSystemService(Service.POWER_SERVICE);
//    		wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "abrinexample");
            wakelock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "abrinexample");
//    		wakelock.setReferenceCounted(false);
        }
        wakelock.acquire();

    }

    /**
     * Releases the currently held wake lock for this client
     */
    private void releaseWakeLock() {
        if (wakelock != null && wakelock.isHeld()) {
            wakelock.release();
        }
    }

    /**
     * Method called on Error
     * For compatibility
     */
//    @Override
//    public void onError(Context context, String errorId) {
//    	switch (Integer.parseInt(errorId)) {
//		case ErrorCodes.NOT_REGISTERED: // not registered
//			System.err.println("Not registered error");
//			AbrinCloudMessaging.getInstance(this).register();
//			break;
//		case ErrorCodes.REGISTER_NOT_SUCCESSFULL: // registr not successfull
//			Log.e(TAG, "Registeration not successfull");
//			break;
//		case ErrorCodes.NCENTER_UNAVAILABLE: // server not available
//			Log.e(TAG, "Server not available");
//			break;
//
//		default:
//			break;
//		}
//    }
    @Override
    public void onError(Context context, int errorId, String cause) {
        System.err.println(cause);
    }

    private void sendNotification(PushObject msg) {

        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(msg.getBody());
        style.setSummaryText(msg.getTitle());

        mBuilder.setContentTitle(msg.getTitle()).setContentText(msg.getBody())
                .setSmallIcon(R.drawable.ic_abrin)
//                .setOngoing(false).
                .setAutoCancel(true)
                .setTicker(msg.getBody()).setWhen(System.currentTimeMillis())
//                .setLights(0xFF27a96b, 1000, 1000)
//                .setNumber(NOTIFICATION_ID)
                .setStyle(style)
        ;

//            mBuilder.setLargeIcon(getBitmapFromURL(ni.getUserImgSrcM()));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(intent);

        mNotifyManager.notify(0, mBuilder.build());
    }
}