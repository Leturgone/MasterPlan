package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewPlanData
import com.app.masterplan.domain.repository.remote.PlanRepository
import java.util.UUID

class CreatePlanUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(newPlan: NewPlanData, document: AttachedDocument? = null,): Result<UUID>{
        return try {
            val createdPlanId = planRepository.createPlan(newPlan,document)
            Result.success(createdPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}