package com.mehedi.notification2204

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // post this token to backend api
        Log.d("TAG", "onNewToken: $token")


    }

    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: NotificationCompat.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"


    override fun onMessageReceived(message: RemoteMessage) {

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val title = message.notification?.title
        val body = message.notification?.body

        showNotification(title!!, body!!)






        super.onMessageReceived(message)
    }


    fun showNotification(title: String, body: String) {

        val intent = Intent(this, MainActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)


            builder = NotificationCompat.Builder(this, channelId)

                .setSmallIcon(R.drawable.baseline_notifications_active_24)

                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentIntent(pendingIntent)
        } else {

            builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(body)

                .setContentIntent(pendingIntent)
        }

        val id = System.currentTimeMillis().toInt()

        notificationManager.notify(id, builder.build())


    }


}