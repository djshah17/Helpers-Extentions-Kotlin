package com.example.apphelper.extentions

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.example.apphelper.R

// Clears notification tray messages
fun Context.clearNotifications() {
    try {
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.cancelAll()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun Context.sendNotification(
    channelId: String?,
    appName: String?,
    notificationIntent: Intent?,
    colorId: Int?,
    iconId: Int?,
    contentTitle: String?,
    contentText: String?
) {
    try {
        // Get an instance of the Notification manager
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the channel for the notification
            val mChannel =
                NotificationChannel(channelId, appName, NotificationManager.IMPORTANCE_HIGH)

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel)
        }
        // Construct a task stack.
        val stackBuilder = TaskStackBuilder.create(this)

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(this as Activity)

        // Push the content Intent onto the stack.
        notificationIntent?.let { stackBuilder.addNextIntent(it) }

        // Get a PendingIntent containing the entire back stack.
        val notificationPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Get a notification builder that's compatible with platform versions >= 4
        val builder = NotificationCompat.Builder(this)

        // Define the notification settings.
        colorId?.let {
            iconId?.let { it1 ->
                builder.setSmallIcon(it1)
                    // In a real app, you may want to use a library like Volley
                    // to decode the Bitmap.
                    .setLargeIcon(BitmapFactory.decodeResource(resources, iconId))
                    .setColor(it)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSound(defaultSoundUri)
                    .setContentIntent(notificationPendingIntent)
            }
        }

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId?.let { builder.setChannelId(it) } // Channel ID
        } else {
            builder.priority = Notification.PRIORITY_HIGH
        }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true)

        // Issue the notification
        mNotificationManager.notify(0, builder.build())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
