package com.app.masterplan.framework.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.framework.websocket.WebSocketWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class WebsocketNotificationService @Inject constructor(
    private val notificationCreator: NotificationCreator,
    private val webSocketWorker: WebSocketWorker
) : Service() {

    private var isConnected = false

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    companion object {
        const val ACTION_SUBSCRIBE = "ACTION_SUBSCRIBE"
        const val ACTION_UNSUBSCRIBE = "ACTION_UNSUBSCRIBE"
    }


    private suspend fun subscribeToUserNotifications(userRole: UserRole, userId: UUID) {
        try {
            webSocketWorker.connectForUser(userRole, userId) { notificationData ->
                notificationCreator.showNotification(notificationData)
            }
            isConnected = true
        } catch (e: Exception) {
            Log.e("WebSocketService", "Ошибка подключения WebSocket", e)
        }
    }

    private fun unsubscribeFromNotifications() {
        webSocketWorker.disconnect()
        isConnected = false
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SUBSCRIBE -> {
                val userId = intent.getSerializableExtra("USER_ID", UUID::class.java)
                val userRole = intent.getSerializableExtra("USER_ROLE", UserRole::class.java)
                if (userId != null && userRole != null) {
                    serviceScope.launch {
                        subscribeToUserNotifications(userRole,userId)
                    }
                }
            }
            ACTION_UNSUBSCRIBE -> {
                serviceScope.launch {
                    unsubscribeFromNotifications()
                }
            }
        }
        return START_REDELIVER_INTENT
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        webSocketWorker.disconnect();
    }



}