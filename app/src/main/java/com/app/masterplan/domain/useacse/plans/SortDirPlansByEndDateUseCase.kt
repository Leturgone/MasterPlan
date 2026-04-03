package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class SortDirPlansByEndDateUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(directorId: UUID): Result<List<Plan>>{
        return try {
            val plans = planRepository.getSortDirPlansByEndDate(directorId)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}