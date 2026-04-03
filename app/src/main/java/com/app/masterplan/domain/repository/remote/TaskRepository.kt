package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewTaskData
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import java.util.UUID

interface TaskRepository {

    suspend fun addTaskToPlan(planId: UUID, newTask: NewTaskData, document: AttachedDocument? = null,): UUID

    suspend fun updateTaskStatus(taskId: UUID, status: TaskStatus): UUID

    suspend fun deleteTask(taskId: UUID): UUID

    suspend fun getFilterAssignedTasksByStatus(executorId: UUID, taskStatus: TaskStatus): List<Task>

    suspend fun getFilterPlanTasksByStatus(planId: UUID, taskStatus: TaskStatus) : List<Task>

    suspend fun getAssignedTasks(executorId: UUID): List<Task>

    suspend fun getTask(taskId: UUID): Task

    suspend fun getTasksFromPlan(planId: UUID): List<Task>

    suspend fun searchAssignedTasksByTitle(query: String, executorId: UUID): List<Task>

    suspend fun getSortAssignedTasksByEndDate(executorId: UUID): List<Task>

    suspend fun getSortPlanTasksByEndDate(planId: UUID): List<Task>

    suspend fun updateTask(taskId: UUID,updatedTask: Task,
                           attachedDocument: AttachedDocument? = null): UUID
}