package com.app.masterplan.data.repository.remote

import com.app.masterplan.data.api.adminRequestsApi.AdminRequestsApi
import com.app.masterplan.data.api.adminRequestsApi.dto.request.CreateAdminAnswerRequest
import com.app.masterplan.data.api.adminRequestsApi.dto.request.CreateAdminRequestRequest
import com.app.masterplan.data.api.adminRequestsApi.dto.request.UpdateRequestStatusRequest
import com.app.masterplan.data.mapper.ApiErrorHandler
import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.AdminRequestMapper
import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.framework.storage.interfaces.TokenDataSource
import java.util.UUID
import javax.inject.Inject

class AdminRequestsRepositoryImpl @Inject constructor(
    private val api: AdminRequestsApi,
    private val tokenStorage: TokenDataSource
): AdminRequestsRepository {

    override suspend fun changeAdminRequestStatus(
        adminRequestId: UUID,
        status: AdminRequestStatus
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage()
        val request = UpdateRequestStatusRequest(status = status.name)
        val response = api.changeAdminRequestStatus(
            token = token,
            requestId = adminRequestId,
            request = request
        )
        return AdminRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun createAdminAnswer(
        title: String,
        description: String,
        adminRequestId: UUID
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage()
        val request = CreateAdminAnswerRequest(
            title = title, description = description
        )
        val response = api.createAdminAnswer(
            token = token,
            requestId = adminRequestId,
            request = request,
        )
        return AdminRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun createAdminRequest(
        title: String,
        description: String,
        senderId: UUID
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage()
        val request = CreateAdminRequestRequest(
            title = title,
            description = description
        )
        val response = api.createAdminRequest(
            token = token,
            senderId =senderId,
            request = request
        )
        return AdminRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getAdminAnswerForRequest(adminRequestId: UUID): AdminAnswer {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = api.getAdminAnswerForRequest(token,adminRequestId)
        return AdminRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getAdminRequestsList(): List<AdminRequest> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = api.getAdminRequestsList(token)
        return ApiErrorHandler.handleResponse(response,::errorMapper).map {
            AdminRequestMapper.toDomain(it)
        }
    }

    override suspend fun getAdminRequest(adminRequestId: UUID): AdminRequest {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = api.getAdminRequest(token,adminRequestId)
        return AdminRequestMapper.toDomain(
            ApiErrorHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getCreatedAdminRequestsListBySender(senderId: UUID): List<AdminRequest> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = api.getCreatedAdminRequestsBySenderList(token,senderId)
        return ApiErrorHandler.handleResponse(response,::errorMapper).map {
            AdminRequestMapper.toDomain(it)
        }
    }

    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.AdminApiException {
        return ApiException.AdminApiException(
            status = errorResp.status,
            apiMessage = errorResp.message,
            timestamp = errorResp.timestamp
        )
    }

}