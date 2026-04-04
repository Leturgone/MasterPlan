package com.app.masterplan.data.api.reportsApi

import com.app.masterplan.data.api.reportsApi.dto.request.CreateReportRequest
import com.app.masterplan.data.api.reportsApi.dto.request.UpdateReportRequest
import com.app.masterplan.data.api.reportsApi.dto.request.UpdateReportStatusRequest
import com.app.masterplan.data.api.reportsApi.dto.responce.ReportIdResponse
import com.app.masterplan.data.api.reportsApi.dto.responce.ReportResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.UUID

interface ReportsApi {

    @GET("/emp/report/{reportId}/type/{reportType}")
    suspend fun getReportInformation(
        @Header("Authorization") token: String,
        @Path(value = "reportType") reportType: String,
        @Path(value = "reportId") reportId: UUID
    ): Response<ReportResponse>


    @GET("/emp/{employeeId}/report/type/{reportType}/all")
    suspend fun getCreatedReports(
        @Header("Authorization") token: String,
        @Path(value = "employeeId") employeeId: UUID,
        @Path(value = "reportType") reportType: String,
    ): Response<List<ReportResponse>>


    @GET("/dir/{directorId}/subordinatesReports/type/TASK")
    suspend fun getSubordinatesTaskReports(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ):Response<List<ReportResponse>>


    @Multipart
    @POST("/emp/report/type/{reportType}")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Path(value = "reportType") reportType: String,
        @Part("request") request: CreateReportRequest,
        @Part(value = "file") file: MultipartBody.Part
    ): Response<ReportIdResponse>

    @Multipart
    @PATCH("/emp/report/{reportId}/type/{reportType}")
    suspend fun  updateReport(
        @Header("Authorization") token: String,
        @Path(value = "reportType") reportType: String,
        @Path(value = "reportId") reportId: UUID,
        @Part(value = "file") file: MultipartBody.Part,
        @Part("request") request: UpdateReportRequest
    ): Response<ReportIdResponse>


    @GET("/dir/{directorId}/subordinatesReports/type/TASK/status/{reportStatus}")
    suspend fun getFilterByStatusSubordinatesTaskReports(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID,
        @Path(value = "reportStatus") reportStatus: String
    ): Response<List<ReportResponse>>


    @GET("/emp/{employeeId}/report/type/{reportType}/status/{reportStatus}")
    suspend fun getFilterByStatusCreatedReports(
        @Header("Authorization") token: String,
        @Path(value = "employeeId") employeeId: UUID,
        @Path(value = "reportType") reportType: String,
        @Path(value = "reportStatus") reportStatus: String
    ): Response<List<ReportResponse>>


    @DELETE(("/emp/report/{reportId}/type/{reportType}"))
    suspend fun deleteReport(
        @Header("Authorization") token: String,
        @Path(value = "reportType") reportType: String,
        @Path(value = "reportId") reportId: UUID,
    ): Response<ReportIdResponse>


    @PATCH(("/dir/report/{reportId}/type/{reportType}/status"))
    suspend fun changeReportStatus(
        @Header("Authorization") token: String,
        @Path(value = "reportType") reportType: String,
        @Path(value = "reportId") reportId: UUID,
        @Body request: UpdateReportStatusRequest
    ): Response<ReportIdResponse>
}