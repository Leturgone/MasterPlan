package com.app.masterplan.domain.useacse.userManagement

import com.app.masterplan.domain.repository.remote.UserRepository
import java.util.UUID

class ResetPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: UUID, newPassword: String): Result<UUID>{
        return try {
            val userWithResetPassword = userRepository.resetPassword(userId,newPassword)
            Result.success(userWithResetPassword)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}