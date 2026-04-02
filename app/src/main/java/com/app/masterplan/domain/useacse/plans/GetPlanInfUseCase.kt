package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class GetPlanInfUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: UUID): Result<Plan>{
        return try {
            val plan = planRepository.getPlan(planId)
            Result.success(plan)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}