package com.app.masterplan.data.api.filesApi.dto.responce

import java.time.LocalDateTime

data class FilesErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)