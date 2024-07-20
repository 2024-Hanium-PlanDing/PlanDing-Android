package com.comst.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PDFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    override fun onNewToken(token: String) {
        Log.d(TAG, "new Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        Log.d(TAG, "Message data : ${remoteMessage.data}")
        Log.d(TAG, "Message noti : ${remoteMessage.notification}")

        if (remoteMessage.data.isNotEmpty()){
            handleNotification(remoteMessage)
        }else{
            Log.d(TAG, "메시지를 수신하지 못했습니다.")
        }
    }

    private fun handleNotification(remoteMessage: RemoteMessage) {
        //sendNotification()
    }

    private fun sendNotification(remoteMessage: RemoteMessage, channelId: String, channelName: String) {
        CoroutineScope(Dispatchers.IO).launch {


            withContext(Dispatchers.Main) {
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val uniId: Int = (System.currentTimeMillis() / 7).toInt()

                val notificationBuilder = NotificationCompat.Builder(
                    this@PDFirebaseMessagingService, channelId
                ).apply {
                    priority = NotificationCompat.PRIORITY_HIGH
                    setSmallIcon(com.comst.presentation.R.drawable.app_icon)
                    setContentTitle(remoteMessage.data["title"])
                    setContentText(remoteMessage.data["text"])
                    setAutoCancel(true)
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

                }

                val channel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)

                notificationManager.notify(uniId, notificationBuilder.build())

            }
        }
    }

    suspend fun getFirebaseToken(): String {
        return withContext(Dispatchers.IO) {
            FirebaseMessaging.getInstance().token.await()
        }
    }
}