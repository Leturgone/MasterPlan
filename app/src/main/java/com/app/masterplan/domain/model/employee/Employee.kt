package com.app.masterplan.domain.model.employee

import java.util.UUID

data class Employee(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? = null,
    val userId: UUID
)
