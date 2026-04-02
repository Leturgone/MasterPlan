package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class FilterDirPlansByStatusUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(directorId: UUID, status: PlanStatus): Result<List<Plan>>{
        return try {
            val plans = planRepository.getFilterDirPlansByStatus(directorId,status)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}