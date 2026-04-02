package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class GetDirPlansUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(directorId: UUID): Result<List<Plan>>{
        return try {
            val plans = planRepository.getDirPlans(directorId)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}