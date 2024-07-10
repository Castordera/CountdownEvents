package com.ulises.common.notification.channels

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class ChannelHandler(
    private val context: Context
) {
    fun deleteNotificationChannel(channelId: String) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(channelId)
    }

    fun createNotificationChannel(channelId: String) {
        val channelName = "Basic Notifications"
        val channelDescription = "This channel is used to handle new updates"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}