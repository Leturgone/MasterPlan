package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class GetTaskInfUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: UUID): Result<Task>{
        return try {
            val task = taskRepository.getTask(taskId)
            Result.success(task)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}