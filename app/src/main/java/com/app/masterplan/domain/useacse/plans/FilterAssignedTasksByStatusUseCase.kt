package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class FilterAssignedTasksByStatusUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(executorId: UUID, taskStatus: TaskStatus): Result<List<Task>>{
        return try {
            val tasks = taskRepository.getFilterAssignedTasksByStatus(executorId,taskStatus)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}