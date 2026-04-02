package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class SortPlanTasksByEndDateUseCase(
    private val tasksRepository: TaskRepository
) {
    suspend operator fun invoke(planId: UUID): Result<List<Task>>{
        return try {
            val tasks = tasksRepository.getSortPlanTasksByEndDate(planId)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}