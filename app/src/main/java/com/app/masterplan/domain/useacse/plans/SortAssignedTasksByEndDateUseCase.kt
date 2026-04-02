package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class SortAssignedTasksByEndDateUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(executorId: UUID): Result<List<Task>>{
        return try {
            val tasks = taskRepository.getSortAssignedTasksByEndDate(executorId)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}