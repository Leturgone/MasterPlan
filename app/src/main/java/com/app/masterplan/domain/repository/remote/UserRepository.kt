package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.NewUserData
import com.app.masterplan.domain.model.userManagement.User
import java.util.UUID

interface UserRepository {

    suspend fun createUser(newUserData: NewUserData): UUID

    suspend fun deleteUser(userId: UUID): UUID

    suspend fun getUserById(userId: UUID): User

    suspend fun getUserByLogin(login: String): User

    suspend fun resetPassword(userId: UUID, newPassword: String): UUID

}