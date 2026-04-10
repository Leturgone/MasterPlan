package com.app.masterplan.domain.useacse.auth

import com.app.masterplan.domain.repository.remote.AuthRepository
import java.util.UUID

class GetUserIdUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<UUID>{
        return try {
            val role = authRepository.getLocalUserId()
            Result.success(role)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}