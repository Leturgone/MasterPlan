package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.NewPlanData
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import java.util.UUID

interface PlanRepository {

    suspend fun updatePlanStatus(planId: UUID, status: PlanStatus): UUID

    suspend fun createPlan(newPlan: NewPlanData): UUID

    suspend fun deletePlan(planId: UUID): UUID

    suspend fun exportPlan(planId: UUID): ByteArray

    suspend fun getFilterDirPlansByStatus(directorId: UUID, status: PlanStatus): List<Plan>

    suspend fun getDirPlans(directorId: UUID): List<Plan>

    suspend fun getPlan(planId: UUID): Plan

}