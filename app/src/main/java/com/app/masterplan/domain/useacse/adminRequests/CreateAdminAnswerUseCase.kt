package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class CreateAdminAnswerUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(title: String,description: String, adminRequestId: UUID): Result<UUID>{
        return try {
            val adminAnswerId = adminRequestsRepository.createAdminAnswer(
                title = title,
                description = description,
                adminRequestId = adminRequestId
            )
            Result.success(adminAnswerId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}