package com.app.masterplan.data.exception

sealed class ApiException(
    val status: Int,
    val apiMessage: String? = "",
    message: String
): Exception(message) {

    class AdminApiException(status: Int,apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "AdminApi Exception: $apiMessage (status: $status) "
    )

    class AuthApiException(status: Int,apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "AuthApi Exception: $apiMessage (status: $status) "
    )

    class FilesApiException(status: Int,apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "FilesApi Exception: $apiMessage (status: $status) "
    )


    class EmployeeApiException(status: Int,apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "EmployeeApi Exception: $apiMessage (status: $status) "
    )


    class PlansTasksApiException(status: Int, apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "PlansTasksApi Exception: $apiMessage (status: $status) "
    )


    class ReportsApiException(status: Int, apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "ReportsApi Exception: $apiMessage (status: $status) "
    )


    class UserManagementApiException(status: Int, apiMessage: String?): ApiException(
        status = status,
        apiMessage = apiMessage,
        message = "UserManagementApi Exception: $apiMessage (status: $status) "
    )
}