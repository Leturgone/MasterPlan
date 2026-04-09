package com.app.masterplan.data.api.plansApi

import com.app.masterplan.data.api.plansApi.dto.request.UpdatePlanStatusRequest
import com.app.masterplan.data.api.plansApi.dto.request.UpdateTaskStatusRequest
import com.app.masterplan.data.api.plansApi.dto.responce.PlanIdResponse
import com.app.masterplan.data.api.plansApi.dto.responce.PlanInformationResponse
import com.app.masterplan.data.api.plansApi.dto.responce.TaskIdResponse
import com.app.masterplan.data.api.plansApi.dto.responce.TaskInformationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.UUID

interface PlansTasksApi {

    @GET("plans/emp/plan/{planId}/")
    suspend fun getPlanInformation(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID
    ): Response<PlanInformationResponse>


    @GET("tasks/emp/task/{taskId}/")
    suspend fun getTaskInformation(
        @Header("Authorization") token: String,
        @Path(value = "taskId") taskId: UUID
    ): Response<TaskInformationResponse>


    @GET("plans/dir/plan/{planId}/tasks")
    suspend fun getPlanTasks(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID
    ): Response<List<TaskInformationResponse>>


    @GET("plans/dir/plan/{planId}/tasks/status/{status}")
    suspend fun getPlanTasksFilterByStatus(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID,
        @Path(value = "status") status: String
    ): Response<List<TaskInformationResponse>>


    @GET("plans/dir/plan/{planId}/tasks/sortTime")
    suspend fun getPlanTasksSortByTime(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID
    ): Response<List<TaskInformationResponse>>


    @GET("tasks/emp/{executorId}/assignedTasks")
    suspend fun getAssignedTasks(
        @Header("Authorization") token: String,
        @Path(value = "executorId") executorId: UUID
    ): Response<List<TaskInformationResponse>>


    @GET("tasks/emp/{executorId}/assignedTasks/search/{query}")
    suspend fun searchAssignedTasksByTitle(
        @Header("Authorization") token: String,
        @Path(value = "executorId") executorId: UUID,
        @Path(value = "query") query: String
    ): Response<List<TaskInformationResponse>>


    @GET("tasks/emp/{executorId}/assignedTasks/status/{status}")
    suspend fun getAssignedTasksFilterByStatus(
        @Header("Authorization") token: String,
        @Path(value = "executorId") executorId: UUID,
        @Path(value = "status") status: String
    ): Response<List<TaskInformationResponse>>


    @GET("tasks/emp/{executorId}/assignedTasks/sortTime")
    suspend fun getAssignedTasksSortByTime(
        @Header("Authorization") token: String,
        @Path(value = "executorId") executorId: UUID
    ): Response<List<TaskInformationResponse>>


    @GET("plans/dir/plan/{planId}/export")
    suspend fun exportPlan(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID
    ): Response<ByteArray>



    @Multipart
    @POST("plans/dir/plan")
    suspend fun createPLan(
        @Header("Authorization") token: String,
        @Part("request") request: RequestBody,
        @Part(value = "file") file: MultipartBody.Part?
    ): Response<PlanIdResponse>

    @Multipart
    @POST("tasks/dir/task")
    suspend fun addTaskToPlan(
        @Header("Authorization") token: String,
        @Part("request") request: RequestBody,
        @Part(value = "file") file: MultipartBody.Part?
    ): Response<TaskIdResponse>


    @GET("plans/dir/{directorId}/plans")
    suspend fun getDirPlans(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<PlanInformationResponse>>


    @GET("plans/dir/{directorId}/plans/status/{status}")
    suspend fun getDirPlansFilterByStatus(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID,
        @Path(value = "status") status: String
    ): Response<List<PlanInformationResponse>>


    @GET("plans/dir/{directorId}/plans/sortTime")
    suspend fun getDirPlansSortByTime(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<PlanInformationResponse>>


    @Multipart
    @PUT("tasks/dir/task/{taskId}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path(value = "taskId") taskId: UUID,
        @Part("request") request: RequestBody,
        @Part(value = "file") file: MultipartBody.Part?
    ): Response<TaskIdResponse>


    @Multipart
    @PUT("plans/dir/plan/{planId}")
    suspend fun updatePlan(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID,
        @Part("request") request: RequestBody,
        @Part(value = "file") file: MultipartBody.Part?
    ): Response<PlanIdResponse>


    @DELETE("plans/dir/plan/{planId}")
    suspend fun deletePlan(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID
    ): Response<PlanIdResponse>


    @DELETE("tasks/dir/task/{taskId}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path(value = "taskId") taskId: UUID
    ): Response<TaskIdResponse>


    @PATCH("plans/dir/plan/{planId}/status")
    suspend fun updatePlanStatus(
        @Header("Authorization") token: String,
        @Path(value = "planId") planId: UUID,
        @Body request: UpdatePlanStatusRequest
    ): Response<PlanIdResponse>


    @PATCH("tasks/dir/task/{taskId}/status")
    suspend fun updateTaskStatus(
        @Header("Authorization") token: String,
        @Path(value = "taskId") taskId: UUID,
        @Body request: UpdateTaskStatusRequest
    ): Response<TaskIdResponse>
}