package com.ulises.common.notification.tokens

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TokenHandler {

    suspend fun getToken(): String = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w("FCM token was not generated")
                continuation.resumeWithException(Exception("FCM token not generated"))
                return@addOnCompleteListener
            }
            Timber.d("FCM token: ${task.result}")
            continuation.resume(task.result)
        }
    }
}