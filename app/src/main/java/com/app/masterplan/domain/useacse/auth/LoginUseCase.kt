package com.app.masterplan.domain.useacse.auth

import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.repository.remote.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String): Result<JwtToken>{
        return try {
            val token = authRepository.login(login,password)
            Result.success(token)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}