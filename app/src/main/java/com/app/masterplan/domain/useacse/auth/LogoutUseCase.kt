package com.app.masterplan.domain.useacse.auth

import com.app.masterplan.domain.repository.remote.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit>{
        return try {
            val token = authRepository.logout()
            Result.success(token)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}