package com.app.masterplan.data.repository.remote

import com.app.masterplan.data.api.authApi.AuthApi
import com.app.masterplan.data.api.authApi.dto.request.LoginRequest
import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorHandler
import com.app.masterplan.data.mapper.AuthRequestMapper
import com.app.masterplan.domain.model.auth.JwtToken
import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.data.storage.TokenDataStorage
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenStorage: TokenDataStorage
): AuthRepository {
    override suspend fun login(
        login: String,
        password: String
    ): JwtToken {
        val request = LoginRequest(
            login = login,
            password = password
        )
        val response = api.login(request)
        val jwtToken = AuthRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
        tokenStorage.saveTokenToDataStorage(jwtToken)
        return jwtToken
    }

    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.AuthApiException {
        return ApiException.AuthApiException(
            status = errorResp.status,
            apiMessage = errorResp.message,
            timestamp = errorResp.timestamp
        )
    }
}