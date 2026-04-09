package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.plansApi.dto.responce.PlanIdResponse
import com.app.masterplan.data.api.plansApi.dto.responce.PlanInformationResponse
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus

object PlanResponseMapper {

    fun toDomain(response: PlanIdResponse) = response.id

    fun toDomain(response: PlanInformationResponse): Plan{

        val status = try {
            PlanStatus.valueOf(response.status)
        }catch (_: Exception){
            throw Exception("Invalid status: ${response.status}")
        }

        return Plan(
            id = response.id,
            title = response.title,
            description = response.description,
            startDate = DateSerializer.toLocalDate(response.startDate),
            endDate = DateSerializer.toLocalDate(response.endDate),
            status = status,
            directorId = response.directorId,
            documentId = response.documentId
        )
    }


    fun toDomain(response: List<PlanInformationResponse>): List<Plan>{
        return response.map { toDomain(it) }
    }
}