package com.ulises.common.notification

import android.annotation.SuppressLint
import android.app.Notification
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Timber.d("New notification upcoming")
        createNotification(message)
    }

    override fun onRegistered(installationId: String) {
        super.onRegistered(installationId)
        Timber.d("onRegistered $installationId")
    }

    override fun onUnregistered(installationId: String) {
        super.onUnregistered(installationId)
        Timber.d("onUnregistered $installationId")
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