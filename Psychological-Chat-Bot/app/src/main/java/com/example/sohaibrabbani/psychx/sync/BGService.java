package com.example.sohaibrabbani.psychx.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.sohaibrabbani.psychx.ChatActivity;
import com.example.sohaibrabbani.psychx.LoginActivity;
import com.example.sohaibrabbani.psychx.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lenovo on 22-Apr-18.
 */

public class BGService extends Service {
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE = "";
    final static int RQS_STOP_SERVICE = 1;

    NotifyServiceReceiver notifyServiceReceiver;

    private static final int MY_NOTIFICATION_ID=1;
    private NotificationManager notificationManager;
    private Notification myNotification;
    private final String myBlog = "http://android-er.blogspot.com/";
    final private Context context =this;

    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        notifyServiceReceiver = new NotifyServiceReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ACTION);
//        registerReceiver(notifyServiceReceiver, intentFilter);



        try {
//            new AsyncTask<Void, Void, Void>(){
                boolean x=true;

//                @Override
//                protected Void doInBackground(Void... voids) {
                    try {
                        for (int i = 0; i >= 0; i++) {
                            Thread.sleep(2000);
                            Date currentTime = Calendar.getInstance().getTime();

                            Log.d("Service 1", currentTime.toString().split(" ")[3]);
                            String h= currentTime.toString().split(" ")[3].split(":")[0];
                            String m=currentTime.toString().split(" ")[3].split(":")[1];
                            Log.d("Service 2", h+"-"+m);
                            Log.d("Service 3", String.valueOf(x));

                            if(h.equals("16") && m.equals("51") && x)
                            {
                                Log.d("Service IN", currentTime.toString().split(" ")[3]);
                                x=false;
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                                mBuilder.setSmallIcon(R.drawable.ic_chat);
                                mBuilder.setContentTitle("PsychX Chat-bot");
                                mBuilder.setContentText("Hi, How are you doing?");

                                Intent resultIntent = new Intent(context, ChatActivity.class);
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                                stackBuilder.addParentStack(LoginActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
                                stackBuilder.addNextIntent(resultIntent);
                                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                                mBuilder.setContentIntent(resultPendingIntent);

                                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                                int notificationID = 109;
                                mNotificationManager.notify(notificationID, mBuilder.build());

                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    return null;
//                }
//            }.execute();
//            for (int i = 0; false; i++) {
//
//                if(false)
//                {
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//                    mBuilder.setSmallIcon(R.drawable.ic_icon);
//                    mBuilder.setContentTitle("PsychX.");
//                    mBuilder.setContentText("Hi, How are you doing?");
//
//                    Intent resultIntent = new Intent(this, MainActivity.class);
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//                    stackBuilder.addParentStack(MainActivity.class);
//
//// Adds the Intent that starts the Activity to the top of the stack
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//                    mBuilder.setContentIntent(resultPendingIntent);
//
//                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//                    int notificationID = 109;
//                    mNotificationManager.notify(notificationID, mBuilder.build());
//                }
//            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(notifyServiceReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int rqs = arg1.getIntExtra("RQS", 0);
            if (rqs == RQS_STOP_SERVICE){
                stopSelf();
            }
        }
    }
}
