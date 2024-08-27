package com.comst.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.comst.domain.util.DateUtils.formatDateAndTime
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URL

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
        val channelName = when (remoteMessage.data["type"]) {
            FcmTopic.DAILY.name -> "매일 알림"
            FcmTopic.GROUP_SCHEDULE.name -> "그룹 일정"
            FcmTopic.PERSONAL_SCHEDULE.name -> "개인 일정"
            FcmTopic.JOIN.name -> "가입 알림"
            FcmTopic.INVITE.name -> "초대 알림"
            FcmTopic.PLANNER.name -> "플래너 알림"
            else -> "공지사항"
        }

        val topicName = remoteMessage.data["type"] ?: FcmTopic.DEFAULT.name
        sendNotification(remoteMessage, topicName, channelName)

    }

    private fun createNotificationTitle(remoteMessage: RemoteMessage): String{
        return when (remoteMessage.data["type"]) {
            FcmTopic.DAILY.name -> "매일 알림"
            FcmTopic.GROUP_SCHEDULE.name -> "그룹 일정"
            FcmTopic.PERSONAL_SCHEDULE.name -> "개인 일정"
            FcmTopic.JOIN.name -> "가입 알림"
            FcmTopic.INVITE.name -> "초대 알림"
            FcmTopic.PLANNER.name -> "플래너 알림"
            else -> "공지사항"
        }
    }
    private fun createNotificationMessage(remoteMessage: RemoteMessage): String {
        return when (remoteMessage.data["type"]) {
            FcmTopic.DAILY.name -> "오늘의 일정을 미리 확인하세요!"

            FcmTopic.GROUP_SCHEDULE.name -> {
                val group = remoteMessage.data["group"] ?: "그룹"
                val title = remoteMessage.data["title"] ?: "일정"
                val date = formatDateAndTime(remoteMessage.data["date"], remoteMessage.data["time"])
                "오늘까지 ${group}의 $title 일정이 ${date}에 예정되어 있습니다."
            }

            FcmTopic.PERSONAL_SCHEDULE.name -> {
                val title = remoteMessage.data["title"] ?: "일정"
                val date = formatDateAndTime(remoteMessage.data["date"], remoteMessage.data["time"])
                "오늘 $title 일정이 ${date}에 예정되어 있습니다."
            }

            FcmTopic.JOIN.name -> "가입 알림"
            FcmTopic.INVITE.name -> "초대 알림"
            FcmTopic.PLANNER.name -> "플래너 알림"
            else -> "공지사항"
        }
    }
    private fun sendNotification(remoteMessage: RemoteMessage, channelId: String, channelName: String) {
        CoroutineScope(Dispatchers.IO).launch {

            // 나중에 그룹 이름 추가
            val title = createNotificationTitle(remoteMessage)
            val message = createNotificationMessage(remoteMessage)

            withContext(Dispatchers.Main) {
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val uniId: Int = (System.currentTimeMillis() / 7).toInt()

                val notificationBuilder = NotificationCompat.Builder(
                    this@PDFirebaseMessagingService, channelId
                ).apply {
                    priority = NotificationCompat.PRIORITY_HIGH
                    setSmallIcon(com.comst.presentation.R.drawable.app_icon)
                    setContentTitle(title)
                    setContentText(message)
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

    enum class FcmTopic {
        DAILY,
        GROUP_SCHEDULE,
        PERSONAL_SCHEDULE,
        JOIN,
        INVITE,
        PLANNER,
        DEFAULT
    }
}