package com.app.masterplan.domain.model.employee

import java.util.UUID

data class EmployeeWithMetrics(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val director: DirectorDetails? = null,
    val rating: Double,
    val workload: Double,
    val assignedTasksCount: Int,
)
