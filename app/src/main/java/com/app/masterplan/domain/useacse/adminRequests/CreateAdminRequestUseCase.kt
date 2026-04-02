package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import java.util.UUID

class CreateAdminRequestUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(title: String, description: String, senderId: UUID): Result<UUID>{
        return try {
            val adminRequestId = adminRequestsRepository.createAdminRequest(
                title = title,
                description = description,
                senderId = senderId
            )
            Result.success(adminRequestId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}