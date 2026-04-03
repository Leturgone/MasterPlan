package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class DeletePlanUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: UUID): Result<UUID>{
        return try {
            val deletedPlanId = planRepository.deletePlan(planId)
            Result.success(deletedPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}