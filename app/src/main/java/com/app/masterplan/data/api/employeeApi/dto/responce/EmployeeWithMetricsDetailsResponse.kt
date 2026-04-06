package com.app.masterplan.data.api.employeeApi.dto.responce

import java.util.UUID

data class EmployeeWithMetricsDetailsResponse (
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val director: DirectorDetailsDto? = null,
    val metrics: EmployeeMetricsDto
)
