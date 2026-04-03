package com.app.masterplan.domain.dto

import java.util.UUID

data class NewEmployeeData(
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID
)