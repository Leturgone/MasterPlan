package com.app.masterplan.data.repository

import com.app.masterplan.data.api.authApi.AuthApi
import com.app.masterplan.data.api.authApi.dto.request.LoginRequest
import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.data.mapper.AuthResponseMapper
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.repository.remote.AuthRepository
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenStorage: TokenDataStorage
): AuthRepository {

    private var userIdCache: UUID? = null

    private var rolesCache: Set<UserRole>? = null

    override suspend fun login(
        login: String,
        password: String
    ): JwtToken {
        val request = LoginRequest(
            login = login,
            password = password
        )
        val response = api.login(request)
        val jwtToken = AuthResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
        tokenStorage.saveTokenToDataStorage(jwtToken)
        return jwtToken
    }

    override suspend fun logout() {
        tokenStorage.removeTokenFromDataStorage()
    }

    override suspend fun getLocalRoles(): Set<UserRole> {
        return rolesCache ?: run {
            val token = tokenStorage.getTokenFromDataStorage()
            val roles = token.roles
            rolesCache = roles
            roles
        }
    }

    override suspend fun getLocalUserId(): UUID {
        return userIdCache ?: run {
            val token = tokenStorage.getTokenFromDataStorage()
            val userId = token.id
            userIdCache = userId
            userId
        }
    }

    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.AuthApiException {
        return ApiException.AuthApiException(
            status = errorResp.status,
            apiMessage = errorResp.message
        )
    }
}