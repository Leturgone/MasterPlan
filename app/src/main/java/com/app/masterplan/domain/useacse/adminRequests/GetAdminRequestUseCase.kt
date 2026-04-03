package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class GetAdminRequestUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(adminRequestId: UUID): Result<AdminRequest>{
        return try {
            val adminRequest = adminRequestsRepository.getAdminRequest(adminRequestId)
            Result.success(adminRequest)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}