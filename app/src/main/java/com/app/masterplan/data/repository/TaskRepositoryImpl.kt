package com.app.masterplan.data.repository

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.api.plansApi.PlansTasksApi
import com.app.masterplan.data.api.plansApi.dto.request.CreateTaskRequest
import com.app.masterplan.data.api.plansApi.dto.request.UpdateTaskRequest
import com.app.masterplan.data.api.plansApi.dto.request.UpdateTaskStatusRequest
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.data.mapper.MultipartCreator
import com.app.masterplan.data.mapper.TaskResponseMapper
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewTaskData
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.domain.repository.remote.TaskRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksApi: PlansTasksApi,
    private val tokenStorage: TokenDataStorage
): TaskRepository {

    override suspend fun addTaskToPlan(
        planId: UUID,
        newTask: NewTaskData,
        document: AttachedDocument?
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = CreateTaskRequest(
            planId = planId,
            title = newTask.title,
            description = newTask.description,
            endDate = newTask.endDate,
            executorsIds = newTask.executorsId
        )
        val requestJson = Gson().toJson(request)

        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

        val filePartBody = document?.let { MultipartCreator.toMultipartBodyPart(it) }

        val response = tasksApi.addTaskToPlan(
            token = token,
            request = requestBody,
            file = filePartBody
        )
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun updateTaskStatus(taskId: UUID, status: TaskStatus): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdateTaskStatusRequest(status.name)
        val response = tasksApi.updateTaskStatus(token,taskId,request)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun deleteTask(taskId: UUID): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.deleteTask(token,taskId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getFilterAssignedTasksByStatus(
        executorId: UUID,
        taskStatus: TaskStatus
    ): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token

        val response = tasksApi.getAssignedTasksFilterByStatus(token,executorId,taskStatus.name)

        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getFilterPlanTasksByStatus(
        planId: UUID,
        taskStatus: TaskStatus
    ): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token

        val response = tasksApi.getPlanTasksFilterByStatus(token,planId,taskStatus.name)

        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getAssignedTasks(executorId: UUID): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.getAssignedTasks(token,executorId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getTask(taskId: UUID): Task {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.getTaskInformation(token,taskId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getTasksFromPlan(planId: UUID): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.getPlanTasks(token,planId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun searchAssignedTasksByTitle(
        query: String,
        executorId: UUID
    ): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.searchAssignedTasksByTitle(token,executorId,query)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getSortAssignedTasksByEndDate(executorId: UUID): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.getAssignedTasksSortByTime(token,executorId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getSortPlanTasksByEndDate(planId: UUID): List<Task> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = tasksApi.getPlanTasksSortByTime(token,planId)
        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun updateTask(
        taskId: UUID,
        updatedTask: Task,
        attachedDocument: AttachedDocument?
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdateTaskRequest(
            id = updatedTask.id,
            title = updatedTask.title,
            description = updatedTask.description,
            urgency = updatedTask.urgency,
            endDate = updatedTask.endDate,
            status = updatedTask.status.name,
            planId = updatedTask.planId,
            documentId = updatedTask.documentId,
            executorsIds = updatedTask.executorsIds
        )

        val requestJson= Gson().toJson(request)
        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

        val filePartBody = attachedDocument?.let { MultipartCreator.toMultipartBodyPart(it) }

        val response = tasksApi.updateTask(
            token = token,
            taskId = taskId,
            request = requestBody,
            file = filePartBody
        )

        return TaskResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.PlansTasksApiException {
        return ApiException.PlansTasksApiException(
            status = errorResp.status,
            apiMessage = errorResp.message
        )
    }

}