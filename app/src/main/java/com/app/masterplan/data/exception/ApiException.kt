package com.app.masterplan.data.exception

import java.time.LocalDateTime

sealed class ApiException(
    val status: Int,
    val apiMessage: String? = "",
    val timestamp: LocalDateTime,
    message: String
): Exception(message) {

    class AdminApiException(status: Int,apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "AdminApi Exception: $apiMessage (status: $status) "
    )
}