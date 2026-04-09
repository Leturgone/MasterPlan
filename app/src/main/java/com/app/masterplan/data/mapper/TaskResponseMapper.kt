package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.plansApi.dto.responce.TaskIdResponse
import com.app.masterplan.data.api.plansApi.dto.responce.TaskInformationResponse
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus

object TaskResponseMapper {

    fun toDomain(response: TaskIdResponse) = response.id

    fun toDomain(response: TaskInformationResponse): Task {

        val status = try {
            TaskStatus.valueOf(response.status)
        }catch (_: Exception){
            throw Exception("Invalid status: ${response.status}")
        }

        return Task(
            id = response.id,
            title = response.title,
            description = response.description,
            urgency = response.urgency,
            endDate = DateSerializer.toLocalDate(response.endDate),
            status = status,
            planId = response.planId,
            documentId = response.documentId,
            executorsIds = response.executorsIds
        )
    }


    fun toDomain(response: List<TaskInformationResponse>): List<Task>{
        return response.map { toDomain(it) }
    }
}