package com.app.masterplan.domain.dto

import java.time.LocalDate
import java.util.UUID

data class NewTaskData(
    val title: String,
    val description: String,
    val endDate: LocalDate,
    val executorsId: MutableList<UUID>
)
