package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.model.adminRequests.AdminAnswer
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class GetAdminAnswerForRequestUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(adminRequestId: UUID): Result<AdminAnswer>{
        return try {
            val answer = adminRequestsRepository.getAdminAnswerForRequest(
                adminRequestId = adminRequestId
            )
            Result.success(answer)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}