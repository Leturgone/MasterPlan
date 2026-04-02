package com.app.masterplan.domain.useacse.adminRequests

import com.app.masterplan.domain.model.adminRequests.AdminRequest
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository

class GetAdminRequestsListUseCase(
    private val adminRequestsRepository: AdminRequestsRepository
) {
    suspend operator fun invoke(): Result<List<AdminRequest>>{
        return try {
            val list  = adminRequestsRepository.getAdminRequestsList()
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}