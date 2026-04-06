package com.app.masterplan.data.api.userManagementApi.dto.responce

import java.util.UUID


data class UserDataResponse(
    val id: UUID,
    val login: String,
    val password: String,
    val roles: Set<String>
)
