package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.model.userManagement.UserRole
import java.util.UUID

interface AuthRepository {
    suspend fun login(login: String, password: String): JwtToken

    suspend fun logout()

    suspend fun getLocalRoles(): Set<UserRole>

    suspend fun getLocalUserId(): UUID
}