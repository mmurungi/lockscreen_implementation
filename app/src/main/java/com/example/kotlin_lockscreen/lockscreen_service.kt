//package com.example.kotlin_lockscreen
//
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.Service
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.graphics.Color
//import android.os.Build
//import android.os.IBinder
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.core.app.NotificationCompat
//import androidx.core.content.ContextCompat
//import java.lang.Exception
//
//class lockscreen_service : Service(){
//
//    private val TAG = "LockscreenService"
//    private var mServiceStartId = 0
//    private var mContext: Context? = null
//    private var mNM: NotificationManager? = null
//
//    private val mLockscreenReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (null != context) {
//                if (intent.action == Intent.ACTION_SCREEN_OFF) {
//                    Log.d(TAG,"---------------------------------Phone screen closed")
//                }
//                if (intent.action == Intent.ACTION_SCREEN_ON) {
//                    //startLockscreenActivity()
//                    startLockscreenActivity()
//                    Log.d(TAG,"---------------------------------Phone screen on")
//                }
//            }
//        }
//    }
//
//    private fun stateRecever(isStartRecever: Boolean) {
//        if (isStartRecever) {
//            try {
//                val filter = IntentFilter()
//                filter.addAction(Intent.ACTION_SCREEN_OFF)
//                filter.addAction(Intent.ACTION_SCREEN_ON)
//                registerReceiver(mLockscreenReceiver, filter)
//            }catch (e: Exception){
//            }
//        } else {
//            try {
//                if (null != mLockscreenReceiver) {
//                    unregisterReceiver(mLockscreenReceiver)
//                }
//            }catch (e: Exception){
//            }
//        }
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        Log.d(TAG,"---------------------------------Service on create")
//        mContext = this
//        mNM = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
//        showNotification()
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        mServiceStartId = startId
//        stateRecever(true)
//        Log.d(TAG,"---------------------------------Service on start")
//
//        if (null != intent) {
//            Log.d(TAG, "$TAG onStartCommand intent  existed")
//        } else {
//            Log.d(TAG, "$TAG onStartCommand intent NOT existed")
//        }
//        return Service.START_STICKY
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onDestroy() {
//        Log.d(TAG,"---------------------------------Service destroyed")
//        stateRecever(false)
//    }
//
//    private fun startLockscreenActivity() {
//        val startLockscreenActIntent = Intent(mContext, LockScreenActivity::class.java)
//        startLockscreenActIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(startLockscreenActIntent)
//    }
//
//    private fun showNotification() {
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
//            startMyOwnForeground()
//        else
//            startForeground(1001, Notification())
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    open fun startMyOwnForeground() {
//        val NOTIFICATION_CHANNEL_ID = "LockScreenService"
//        val channelName = "LockScreenChannel"
//        val chan = NotificationChannel(
//            NOTIFICATION_CHANNEL_ID,
//            channelName,
//            NotificationManager.IMPORTANCE_NONE
//        )
//        chan.lightColor = Color.BLUE
//        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?)!!
//        manager.createNotificationChannel(chan)
//        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//        val notification: Notification = notificationBuilder.setOngoing(true)
//            .setContentTitle(resources.getString(R.string.service_running_in_background))
//            .setPriority(NotificationManager.IMPORTANCE_MIN)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .setSmallIcon(R.drawable.push_icon)
//            .setColor(ContextCompat.getColor(this,R.color.colorAccent))
//            .build()
//        startForeground(1001, notification)
//    }
//
//
//}}