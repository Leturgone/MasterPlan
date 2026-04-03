package com.app.masterplan.domain.useacse.userManagement

import com.app.masterplan.domain.repository.remote.UserRepository
import java.util.UUID

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: UUID): Result<UUID>{
        return try {
            val deletedUserId = userRepository.deleteUser(userId)
            Result.success(deletedUserId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}