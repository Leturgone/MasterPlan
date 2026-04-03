package com.app.masterplan.domain.useacse.userManagement

import com.app.masterplan.domain.dto.NewUserData
import com.app.masterplan.domain.repository.remote.UserRepository
import java.util.UUID

class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(newUserData: NewUserData): Result<UUID>{
        return try {
            val userId = userRepository.createUser(newUserData)
            Result.success(userId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}