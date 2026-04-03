package com.app.masterplan.domain.useacse.userManagement

import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.repository.remote.UserRepository

class GetUserByLoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(login: String): Result<User>{
        return try {
            val user = userRepository.getUserByLogin(login)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}