package com.app.masterplan.domain.dto

import com.app.masterplan.domain.model.userManagement.UserRole

data class NewUserData(
    val login: String,
    val password: String,
    val roles: List<UserRole>,
    val employeeInfo: NewEmployeeData
)

