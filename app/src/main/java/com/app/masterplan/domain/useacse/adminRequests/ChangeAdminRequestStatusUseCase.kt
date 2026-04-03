package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.model.adminRequests.AdminRequestStatus
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class ChangeAdminRequestStatusUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(adminRequestId: UUID, status: AdminRequestStatus): Result<UUID> {
        return try {
            val adminRequestId = adminRequestsRepository.changeAdminRequestStatus(
                adminRequestId = adminRequestId,
                status = status
            )
            Result.success(adminRequestId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}