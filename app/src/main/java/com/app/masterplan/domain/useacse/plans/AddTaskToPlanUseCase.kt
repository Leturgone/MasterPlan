package com.app.masterplan.domain.useacse.plans

import com.app.masterplan.domain.dto.NewTaskData
import com.app.masterplan.domain.repository.remote.TaskRepository
import java.util.UUID

class AddTaskToPlanUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(planId: UUID, newTask: NewTaskData): Result<UUID>{
        return try {
            val taskId = taskRepository.addTaskToPlan(planId,newTask)
            Result.success(taskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}