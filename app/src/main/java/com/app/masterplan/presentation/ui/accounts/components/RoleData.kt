package com.app.masterplan.presentation.ui.accounts.components

import com.app.masterplan.domain.model.userManagement.UserRole

data class RoleData(
    val role: UserRole,
    var isSelected: Boolean = false
)
