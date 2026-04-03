package com.app.masterplan.data.api.employeeApi.dto.request

import java.util.UUID

data class CreateEmployeeRequest(
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? = null,
    val userId: UUID
)
