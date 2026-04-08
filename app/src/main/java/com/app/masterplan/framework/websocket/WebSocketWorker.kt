package com.app.masterplan.framework.websocket

import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.model.notification.PushNotification
import com.app.masterplan.domain.model.userManagement.UserRole
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.UUID

class WebSocketWorker(
    private val okHttpClient: OkHttpClient,
    private val tokenStorage: TokenDataStorage
) {

    private  lateinit var webSocket: WebSocket

    private lateinit var notificationCallback: ((PushNotification) -> Unit)


    suspend fun connectForUser(
        userRole: UserRole,
        userId: UUID,
        onNotification: ((PushNotification) -> Unit)
    ){
        notificationCallback = onNotification

        val token = tokenStorage.getTokenFromDataStorage()

        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .addHeader("Authorization", "Bearer $token")
            .build()

        webSocket = okHttpClient.newWebSocket(request,object: WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                val connectMessage = buildConnectMessage()
                webSocket.send(connectMessage)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                when{
                    text.startsWith("CONNECTED") -> {
                        when(userRole){
                            UserRole.ADMIN -> buildSubscribeMessageForAdmin()
                            UserRole.EMPLOYEE -> buildSubscribeMessage(userId)
                            UserRole.DIRECTOR -> buildSubscribeMessage(userId)
                        }
                    }
                    text.startsWith("MESSAGE") -> {
                        val notificationData = NotificationMapper.toDomain(text)
                        notificationCallback.invoke(notificationData)
                    }
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                println("WebSocket closing: $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                println("WebSocket closed: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                t.printStackTrace()
            }
        })


    }

    fun disconnect() {
        webSocket.close(1000, "Disconnecting")
    }


    private fun buildConnectMessage(): String {
        return """
            CONNECT
            upgrade:websocket
            accept-version:1.2,1.1,1.0
            heart-beat:0,0

            \u0000
        """.trimIndent()
    }

    private fun buildSubscribeMessage(userId: UUID): String {
        val topic = "/topic/notifications/user/$userId"
        return """
            SUBSCRIBE
            id:sub-0
            destination:$topic
            acknowledge:client

            \u0000
        """.trimIndent()
    }


    private fun buildSubscribeMessageForAdmin(): String {
        val topic = "/topic/notifications/admin/"

        return """
            SUBSCRIBE
            id:sub-0
            destination:$topic
            acknowledge:client

            \u0000
        """.trimIndent()
    }


}