package com.app.masterplan.data.api.userManagementApi.dto.request

import java.util.UUID

data class CreateProfileRequest(
    val login: String,
    val password: String,
    val roles: List<String>,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? =  null

)
