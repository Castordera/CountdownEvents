package com.ulises.common.notification

import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FcmService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Timber.d("New notification upcoming")
        createNotification(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("New token generated $token")
    }

    private fun createNotification(message: RemoteMessage) {
        val builder = NotificationCompat.Builder(this, "basic-notification")
//            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }
}