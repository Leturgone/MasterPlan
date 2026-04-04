package com.app.masterplan.data.api.authApi

import com.app.masterplan.data.api.authApi.dto.request.LoginRequest
import com.app.masterplan.data.api.authApi.dto.responce.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {

    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}