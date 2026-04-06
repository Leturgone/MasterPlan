package com.app.masterplan.data.api.employeeApi.dto.responce

import java.util.UUID

data class EmployeeDetailsResponse(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? = null,
    val userId: UUID,
)
