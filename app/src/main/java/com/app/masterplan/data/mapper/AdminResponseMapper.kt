package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminAnswerIdResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminAnswerResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminRequestIdResponse
import com.app.masterplan.data.api.adminRequestsApi.dto.responce.AdminRequestResponse
import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus

object AdminResponseMapper {

    fun toDomain(resp: AdminAnswerIdResponse) = resp.id

    fun toDomain(resp: AdminAnswerResponse): AdminAnswer{
        return AdminAnswer(
            id = resp.id,
            title = resp.title,
            description = resp.description,
            adminRequestId = resp.adminRequestId
        )
    }

    fun toDomain(resp: AdminRequestIdResponse) = resp.id

    fun toDomain(resp: AdminRequestResponse): AdminRequest {
        val status = try {
            AdminRequestStatus.valueOf(resp.status)
        }catch (e: Exception){
            AdminRequestStatus.INVALID
        }
        return AdminRequest(
            id = resp.id,
            title = resp.title,
            description = resp.description,
            creationDate = resp.creationDate,
            senderId = resp.senderId,
            status = status
        )
    }


}