package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class ChangePlanStatusUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: UUID, status: PlanStatus): Result<UUID>{
        return try {
            val updatedStatusPlanId = planRepository.updatePlanStatus(planId,status)
            Result.success(updatedStatusPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}