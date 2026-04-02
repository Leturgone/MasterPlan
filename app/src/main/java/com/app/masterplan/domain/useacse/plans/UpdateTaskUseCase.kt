package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: UUID,updatedTask: Task, attachedDocument: AttachedDocument? = null): Result<UUID>{
        return try {
            val updatedTaskId = taskRepository.updateTask(taskId,updatedTask,attachedDocument)
            Result.success(updatedTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}