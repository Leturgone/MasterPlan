package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class DeleteTaskFromPlanUseCase(
    private val taskRepository: TaskRepository
){
    suspend operator fun invoke(taskId: UUID): Result<UUID>{
        return try {
            val deletedTaskId = taskRepository.deleteTask(taskId)
            Result.success(deletedTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}