package com.app.masterplan.domain.useacse.userManagement

import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.repository.remote.UserRepository
import java.util.UUID

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: UUID): Result<User>{
        return try {
            val user = userRepository.getUserById(userId)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}