package com.app.masterplan.data.api.userManagementApi

import com.app.masterplan.data.api.userManagementApi.dto.request.CreateProfileRequest
import com.app.masterplan.data.api.userManagementApi.dto.request.ResetPasswordRequest
import com.app.masterplan.data.api.userManagementApi.dto.responce.UserDataResponse
import com.app.masterplan.data.api.userManagementApi.dto.responce.UserUidResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface UserManagementApi {

    @POST("user")
    fun createUser(
        @Header("Authorization") token: String,
        @Body request: CreateProfileRequest
    ): Response<UserUidResponse>


    @DELETE("user/{id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path(value = "id")id: UUID
    ): Response<UserUidResponse>


    @GET("user/login/{login}")
    fun getUserByLogin(
        @Header("Authorization") token: String,
        @Path(value = "login") login: String
    ): Response<UserDataResponse>


    @GET("user/id/{id}")
    fun getUserById(
        @Header("Authorization") token: String,
        @Path(value = "id") id: UUID
    ): Response<UserDataResponse>

    @PATCH("user/{id}/password")
    fun resetPassword(
        @Header("Authorization") token: String,
        @Path(value = "id") id: UUID,
        @Body request: ResetPasswordRequest
    ): Response<UserUidResponse>

}