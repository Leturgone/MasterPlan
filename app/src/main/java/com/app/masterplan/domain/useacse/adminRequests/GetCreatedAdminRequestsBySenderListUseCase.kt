package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class GetCreatedAdminRequestsBySenderListUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(senderId: UUID): Result<List<AdminRequest>>{
        return try {
            val list = adminRequestsRepository.getCreatedAdminRequestsListBySender(senderId)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}