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

    class AuthApiException(status: Int,apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "AuthApi Exception: $apiMessage (status: $status) "
    )

    class FilesApiException(status: Int,apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "FilesApi Exception: $apiMessage (status: $status) "
    )


    class EmployeeApiException(status: Int,apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "EmployeeApi Exception: $apiMessage (status: $status) "
    )


    class PlansTasksApiException(status: Int, apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "PlansTasksApi Exception: $apiMessage (status: $status) "
    )


    class ReportsApiException(status: Int, apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "ReportsApi Exception: $apiMessage (status: $status) "
    )


    class UserManagementApiException(status: Int, apiMessage: String?, timestamp: LocalDateTime): ApiException(
        status = status,
        apiMessage = apiMessage,
        timestamp = timestamp,
        message = "UserManagementApi Exception: $apiMessage (status: $status) "
    )
}