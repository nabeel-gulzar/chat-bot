//package com.example.sohaibrabbani.psychx.sync
//
//import android.app.Notification
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.app.Service
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.os.AsyncTask
//import android.os.IBinder
//import android.support.v4.app.NotificationCompat
//import android.support.v4.app.TaskStackBuilder
//import android.util.Log
//import com.example.sohaibrabbani.psychx.ChatBotActivity
//import com.example.sohaibrabbani.psychx.DashboardActivity
//import com.example.sohaibrabbani.psychx.LoginActivity
//import java.util.*
//
///**
// * Created by Lenovo on 22-Apr-18.
// */
//public class BackgroundService: Service()
//{
//
//    internal val ACTION = "NotifyServiceAction"
//    internal val STOP_SERVICE = ""
//    internal val RQS_STOP_SERVICE = 1
//
////    lateinit var notifyServiceReceiver: NotifyServiceReceiver
//
//    private val MY_NOTIFICATION_ID = 1
//    private val notificationManager: NotificationManager? = null
//    private val myNotification: Notification? = null
//    private val myBlog = "http://android-er.blogspot.com/"
//    private val context = this
//
////    override fun onCreate() {
////        // TODO Auto-generated method stub
//////        notifyServiceReceiver = NotifyServiceReceiver()
////        super.onCreate()
////    }
//
//    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//
//
//        try {
//            object : AsyncTask<Void, Void, Void>() {
//                internal var x = true
//
//                override fun doInBackground(vararg voids: Void): Void? {
//                    try {
//                        var i = 0
//                        while (i >= 0) {
//                            Thread.sleep(2000)
//                            val currentTime = Calendar.getInstance().time
//
//                            Log.d("Service 1", currentTime.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3])
//                            val h = currentTime.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
//                            val m = currentTime.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
//                            Log.d("Service 2", "$h-$m")
//                            Log.d("Service 3", x.toString())
//
//                            if (h == "16" && m == "25" && x) {
//                                Log.d("Service IN", currentTime.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3])
//                                x = false
//                                val mBuilder = NotificationCompat.Builder(context)
////                                mBuilder.setSmallIcon(R.drawable.ic_icon)
//                                mBuilder.setContentTitle("PsychX.")
//                                mBuilder.setContentText("Hi, How are you doing?")
//
//                                val resultIntent = Intent(context, ChatBotActivity::class.java)
//                                val stackBuilder = TaskStackBuilder.create(context)
//                                stackBuilder.addParentStack(LoginActivity::class.java!!)
//
//                                // Adds the Intent that starts the Activity to the top of the stack
//                                stackBuilder.addNextIntent(resultIntent)
//                                val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
//                                mBuilder.setContentIntent(resultPendingIntent)
//
//                                val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//                                // notificationID allows you to update the notification later on.
//                                val notificationID = 109
//                                mNotificationManager.notify(notificationID, mBuilder.build())
//
//                            }
//                            i++
//                        }
//
//                    } catch (e: InterruptedException) {
//                        e.printStackTrace()
//                    }catch (e: Exception)
//                    {
//                        e.printStackTrace()
//                    }
//
//                    return null
//                }
//            }.execute()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return super.onStartCommand(intent, flags, startId)
//    }
//
////    override fun onDestroy() {
//////        this.unregisterReceiver(notifyServiceReceiver)
////        super.onDestroy()
////    }
//
//    override fun onBind(arg0: Intent): IBinder? {
//        return null
//    }
//
////    inner class NotifyServiceReceiver : BroadcastReceiver() {
////
////        override fun onReceive(arg0: Context, arg1: Intent) {
////            val rqs = arg1.getIntExtra("RQS", 0)
////            if (rqs == RQS_STOP_SERVICE) {
////                stopSelf()
////            }
////        }
////    }
//}