package com.app.masterplan.presentation.ui.accounts.components


import java.io.Serializable
import java.util.UUID

data class DirectorData(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? = null,
    val userId: UUID
): Serializable