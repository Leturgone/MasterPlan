package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import java.util.UUID

interface AdminRequestsRepository {

    // Возврат id запроса
    suspend fun changeAdminRequestStatus(adminRequestId: UUID, status: AdminRequestStatus): UUID

    // Возврат id ответа
    suspend fun createAdminAnswer(title: String,description: String, adminRequestId: UUID): UUID

    suspend fun createAdminRequest(title: String, description: String, senderId: UUID): UUID

    suspend fun getAdminAnswerForRequest(adminRequestId: UUID): AdminAnswer

    suspend fun getAdminRequestsList(): List<AdminRequest>

    suspend fun getAdminRequest(adminRequestId: UUID): AdminRequest

    suspend fun getCreatedAdminRequestsListBySender(senderId: UUID): List<AdminRequest>


}