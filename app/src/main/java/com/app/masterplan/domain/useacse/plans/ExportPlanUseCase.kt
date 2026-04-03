package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class ExportPlanUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: UUID): Result<ByteArray>{
        return try {
            val exportedPlan = planRepository.exportPlan(planId)
            Result.success(exportedPlan)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}