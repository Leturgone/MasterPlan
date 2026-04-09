package com.app.masterplan.data.api.exception

data class ApiErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: String,
)