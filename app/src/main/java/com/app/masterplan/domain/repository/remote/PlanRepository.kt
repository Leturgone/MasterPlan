package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.NewPlanDto
import com.app.masterplan.domain.model.plans.PlanStatus
import java.util.UUID

interface PlanRepository {

    suspend fun updatePlanStatus(planId: UUID, status: PlanStatus): UUID

    suspend fun createPlan(newPlan: NewPlanDto): UUID

    suspend fun deletePlan(planId: UUID): UUID

    suspend fun exportPlan(planId: UUID): ByteArray



}