package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.NewTaskData
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import java.util.UUID

interface TaskRepository {

    suspend fun addTaskToPlan(planId: UUID, newTask: NewTaskData): UUID

    suspend fun updateTaskStatus(taskId: UUID, status: TaskStatus): UUID

    suspend fun deleteTask(taskId: UUID): UUID

    suspend fun getFilterAssignedTasksByStatus(executorId: UUID, taskStatus: TaskStatus): List<Task>

    suspend fun getFilterPlanTasksByStatus(planId: UUID, taskStatus: TaskStatus) : List<Task>
}