package com.app.masterplan.domain.useacse.auth

import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.repository.remote.AuthRepository

class GetUserRoleUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Set<UserRole>>{
        return try {
            val role = authRepository.getLocalRoles()
            Result.success(role)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}