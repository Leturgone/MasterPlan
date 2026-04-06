package com.app.masterplan.data.repository.remote

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.api.userManagementApi.UserManagementApi
import com.app.masterplan.data.api.userManagementApi.dto.request.CreateProfileRequest
import com.app.masterplan.data.api.userManagementApi.dto.request.ResetPasswordRequest
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorHandler
import com.app.masterplan.data.mapper.UserResponseMapper
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.dto.NewUserData
import com.app.masterplan.domain.model.userManagement.User
import com.app.masterplan.domain.repository.remote.UserRepository
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userManagementApi: UserManagementApi,
    private val tokenStorage: TokenDataStorage
): UserRepository {


    override suspend fun createUser(newUserData: NewUserData): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = CreateProfileRequest(
            login = newUserData.login,
            password = newUserData.password,
            roles = newUserData.roles.map { it.toString() },
            name = newUserData.employeeInfo.name,
            surname = newUserData.employeeInfo.surname,
            patronymic = newUserData.employeeInfo.patronymic,
            directorId = newUserData.employeeInfo.directorId
        )
        val response = userManagementApi.createUser(token,request)

        return UserResponseMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun deleteUser(userId: UUID): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = userManagementApi.deleteUser(token,userId)
        return UserResponseMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getUserById(userId: UUID): User {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = userManagementApi.getUserById(token,userId)
        return UserResponseMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getUserByLogin(login: String): User {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = userManagementApi.getUserByLogin(token,login)
        return UserResponseMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun resetPassword(userId: UUID, newPassword: String): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = ResetPasswordRequest(newPassword)
        val response = userManagementApi.resetPassword(token, userId, request)
        return UserResponseMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }


    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.UserManagementApiException {
        return ApiException.UserManagementApiException(
            status = errorResp.status,
            apiMessage = errorResp.message,
            timestamp = errorResp.timestamp
        )
    }

}