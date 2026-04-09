package com.app.masterplan.data.repository

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.api.plansApi.PlansTasksApi
import com.app.masterplan.data.api.plansApi.dto.request.CreatePlanRequest
import com.app.masterplan.data.api.plansApi.dto.request.UpdatePlanRequest
import com.app.masterplan.data.api.plansApi.dto.request.UpdatePlanStatusRequest
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.data.mapper.MultipartCreator
import com.app.masterplan.data.mapper.PlanResponseMapper
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewPlanData
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.domain.model.plans.PlanStatus
import com.app.masterplan.domain.repository.remote.PlanRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.UUID
import javax.inject.Inject

class PlanRepositoryImpl  @Inject constructor(
    private val planApi: PlansTasksApi,
    private val tokenStorage: TokenDataStorage,
    private val localFileDataSource: LocalFileDataStorage
): PlanRepository {

    override suspend fun updatePlanStatus(
        planId: UUID,
        status: PlanStatus
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdatePlanStatusRequest(status.name)
        val response = planApi.updatePlanStatus(token, planId, request)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun createPlan(
        newPlan: NewPlanData,
        document: AttachedDocument?
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = CreatePlanRequest(
            id = null,
            title = newPlan.title,
            description = newPlan.description,
            startDate = newPlan.startDate,
            endDate = newPlan.endDate,
            directorId = newPlan.directorId
        )
        val requestJson = Gson().toJson(request)

        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

        val filePartBody = document?.let { MultipartCreator.toMultipartBodyPart(it) }

        val response = planApi.createPLan(
            token = token,
            request = requestBody,
            file = filePartBody,
        )
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun deletePlan(planId: UUID): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.deletePlan(token,planId)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun exportPlan(planId: UUID): File {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.exportPlan(token,planId)
        val bytes = ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        val savedFile = localFileDataSource.saveFileToDataStorage(bytes)
        return savedFile
    }


    override suspend fun getFilterDirPlansByStatus(
        directorId: UUID,
        status: PlanStatus
    ): List<Plan> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.getDirPlansFilterByStatus(token,directorId,status.name)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getDirPlans(directorId: UUID): List<Plan> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.getDirPlans(token,directorId)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getPlan(planId: UUID): Plan {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.getPlanInformation(token,planId)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getSortDirPlansByEndDate(directorId: UUID): List<Plan> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = planApi.getDirPlansSortByTime(token,directorId)
        return PlanResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun updatePlan(
        planId: UUID,
        updatedPlan: Plan,
        attachedDocument: AttachedDocument?
    ): UUID {
        updatedPlan.directorId?: throw Exception("Director id cant be blank")
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdatePlanRequest(
            id = updatedPlan.id,
            title = updatedPlan.title,
            description = updatedPlan.description,
            startDate = updatedPlan.startDate,
            endDate = updatedPlan.endDate,
            directorId = updatedPlan.directorId,
            documentId = updatedPlan.documentId,
            status = updatedPlan.status.name
        )
        val requestJson= Gson().toJson(request)
        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

        val filePartBody = attachedDocument?.let { MultipartCreator.toMultipartBodyPart(it) }
        val response = planApi.updatePlan(
            token = token,
            planId = planId,
            request = requestBody,
            file = filePartBody,
        )
        return PlanResponseMapper.toDomain(
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