package com.mehedi.notification2204


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder

    lateinit var btnNotif: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        btnNotif = findViewById(R.id.btnNotif)

        btnNotif.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("key", "From Notification")
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                101,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )



            builder = NotificationCompat.Builder(this, "n_channel")
                .setContentTitle("just a tittle")
                .setContentText("just a body")
                .setLargeIcon(getMyBitmap(R.drawable.baseline_notifications_active_24))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent)



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        "n_channel",
                        "",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }

            val id = System.currentTimeMillis().toInt()

            notificationManager.notify(id, builder.build())

        }

    }


    private fun getMyBitmap(imgres: Int): Bitmap? {

        return BitmapFactory.decodeResource(resources, imgres)
    }


}