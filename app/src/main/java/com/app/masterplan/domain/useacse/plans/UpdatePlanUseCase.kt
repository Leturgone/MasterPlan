package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class UpdatePlanUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: UUID,updatedPlan: Plan, attachedDocument: AttachedDocument? = null): Result<UUID>{
        return try {
            val updatedPlanId = planRepository.updatePlan(planId,updatedPlan,attachedDocument)
            Result.success(updatedPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}