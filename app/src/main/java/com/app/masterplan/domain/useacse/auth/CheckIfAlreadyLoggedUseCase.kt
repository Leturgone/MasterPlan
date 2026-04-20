package com.app.masterplan.domain.useacse.auth

import com.app.masterplan.domain.repository.remote.AuthRepository

class CheckIfAlreadyLoggedUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(): Result<Boolean>{
        return try {
            val result  = authRepository.isLogged()
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
