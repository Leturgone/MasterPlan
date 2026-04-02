package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class ChangeTaskStatusUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: UUID, status: TaskStatus): Result<UUID>{
        return try {
            val updatedStatusTaskId = taskRepository.updateTaskStatus(taskId,status)
            Result.success(updatedStatusTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}