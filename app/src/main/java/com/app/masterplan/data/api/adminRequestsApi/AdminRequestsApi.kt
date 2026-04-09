package com.app.masterplan.data.api.adminRequestsApi

import com.app.masterplan.data.api.adminRequestsApi.dto.request.CreateAdminAnswerRequest
import com.app.masterplan.data.api.adminRequestsApi.dto.request.CreateAdminRequestRequest
import com.app.masterplan.data.api.adminRequestsApi.dto.request.UpdateRequestStatusRequest
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminAnswerIdResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminAnswerResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminRequestIdResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminRequestResponse
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface AdminRequestsApi {

    @PATCH("admin/requests/{requestId}/status")
    suspend fun changeAdminRequestStatus(
        @Header("Authorization") token: String,
        @Path(value = "requestId") requestId: UUID,
        @Body request: UpdateRequestStatusRequest
    ): Response<AdminRequestIdResponse>


    @POST("admin/requests/{requestId}/answer")
    suspend fun createAdminAnswer(
        @Header("Authorization") token: String,
        @Path(value = "requestId") requestId: UUID,
        @Body request: CreateAdminAnswerRequest
    ): Response<AdminAnswerIdResponse>

    @POST("dir/requests/sender/{senderId}")
    suspend  fun createAdminRequest(
        @Header("Authorization") token: String,
        @Path(value = "senderId") senderId: UUID,
        @Body request: CreateAdminRequestRequest
    ): Response<AdminRequestIdResponse>

    @GET(("dir/requests/{requestId}/answer"))
    suspend fun getAdminAnswerForRequest(
        @Header("Authorization") token: String,
        @Path(value = "requestId") requestId: UUID
    ): Response<AdminAnswerResponse>

    @GET(("dir/requests"))
    suspend fun getAdminRequestsList(
        @Header("Authorization") token: String
    ): Response<List<AdminRequestResponse>>

    @GET(("dir/requests/{requestId}"))
    suspend fun getAdminRequest(
        @Header("Authorization") token: String,
        @Path(value = "requestId") requestId: UUID
    ): Response<AdminRequestResponse>

    @GET(("dir/requests/sender/{senderId}"))
    suspend fun getCreatedAdminRequestsBySenderList(
        @Header("Authorization") token: String,
        @Path(value = "senderId") senderId: UUID
    ): Response<List<AdminRequestResponse>>

}