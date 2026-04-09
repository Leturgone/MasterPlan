package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.model.auth.JwtToken

interface AuthRepository {
    suspend fun login(login: String, password: String): JwtToken

    suspend fun logout()
}