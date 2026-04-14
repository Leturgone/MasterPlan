package com.app.masterplan.data.repository

import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.api.reportsApi.ReportsApi
import com.app.masterplan.data.api.reportsApi.dto.request.CreateReportRequest
import com.app.masterplan.data.api.reportsApi.dto.request.UpdateReportRequest
import com.app.masterplan.data.api.reportsApi.dto.request.UpdateReportStatusRequest
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.data.mapper.MultipartCreator
import com.app.masterplan.data.mapper.ReportResponseMapper
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.dto.UpdateReportData
import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportsApi: ReportsApi,
    private val tokenStorage: TokenDataStorage,
): ReportRepository {



    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
            JsonPrimitive(src.toString())
        })
        .create()

    override suspend fun updateReportStatus(
        reportId: UUID,
        status: ReportStatus,
        type: ReportType
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdateReportStatusRequest(status.name)
        val response = reportsApi.changeReportStatus(
            token = token,
            reportType =type.name,
            reportId = reportId,
            request = request
        )
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun createReport(
        newReportData: NewReportData,
        document: AttachedDocument,
        reportType: ReportType
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token

        val request = CreateReportRequest(
            title = newReportData.title,
            description = newReportData.description,
            employeeId = newReportData.employeeId,
            referenceId = newReportData.referenceId
        )
        val requestJson = gson.toJson(request)
        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
        val filePartBody = MultipartCreator.toMultipartBodyPart(document)

        val response = reportsApi.createReport(
            token = token,
            reportType = reportType.name,
            request = requestBody,
            file = filePartBody
        )
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun deleteReport(
        reportId: UUID,
        reportType: ReportType
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = reportsApi.deleteReport(token,reportType.name,reportId)
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getFilterByStatusCreatedReports(
        employeeId: UUID,
        status: ReportStatus,
        reportType: ReportType
    ): List<Report> {
        val token = tokenStorage.getTokenFromDataStorage().token
        
        val response = reportsApi.getFilterByStatusCreatedReports(
            token = token,
            employeeId = employeeId,
            reportType = reportType.name,
            reportStatus = status.name
        )

        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getFilterByStatusSubordinatesTaskReports(
        directorId: UUID,
        status: ReportStatus
    ): List<Report> {
        val token = tokenStorage.getTokenFromDataStorage().token

        val response = reportsApi.getFilterByStatusSubordinatesTaskReports(
            token = token,
            directorId = directorId,
            reportStatus = status.name
        )
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getCreatedReports(
        employeeId: UUID,
        reportType: ReportType
    ): List<Report> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = reportsApi.getCreatedReports(token,employeeId,reportType.name)
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getReport(
        reportId: UUID,
        reportType: ReportType
    ): Report {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = reportsApi.getReportInformation(token,reportType.name,reportId)
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun getSubordinatesTaskReports(directorId: UUID): List<Report> {
        val token = tokenStorage.getTokenFromDataStorage().token
        val response = reportsApi.getSubordinatesTaskReports(token,directorId)
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    override suspend fun updateReport(
        reportId: UUID,
        updatedData: UpdateReportData,
        reportType: ReportType,
        document: AttachedDocument
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage().token
        val request = UpdateReportRequest(
            title = updatedData.title,
            description = updatedData.description,
            documentId = updatedData.documentId
        )
        val requestJson = gson.toJson(request)
        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
        val filePartBody = MultipartCreator.toMultipartBodyPart(document)

        val response = reportsApi.updateReport(
            token = token,
            reportType = reportType.name,
            reportId = reportId,
            file = filePartBody,
            request = requestBody
        )
        return ReportResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.ReportsApiException {
        return ApiException.ReportsApiException(
            status = errorResp.status,
            apiMessage = errorResp.message
        )
    }


}