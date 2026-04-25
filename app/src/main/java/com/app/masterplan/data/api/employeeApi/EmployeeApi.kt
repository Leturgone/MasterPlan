package com.app.masterplan.data.api.employeeApi

import com.app.masterplan.data.api.employeeApi.dto.request.CreateEmployeeRequest
import com.app.masterplan.data.api.employeeApi.dto.request.UpdateEmployeeRequest
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeDetailsResponse
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeIdResponse
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeWithMetricsDetailsResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface EmployeeApi {

    @POST("admin/employee")
    suspend fun createEmployee(
        @Header("Authorization") token: String,
        @Body request: CreateEmployeeRequest
    ): Response<EmployeeIdResponse>


    @GET("dir/employee/{directorId}/employees/export")
    suspend fun exportDirEmployees(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<ResponseBody>


    @GET("dir/employee/{directorId}/employees")
    suspend fun getDirEmployees(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<EmployeeDetailsResponse>>


    @GET("admin/employee")
    suspend fun getAllEmployees(
        @Header("Authorization") token: String,
    ): Response<List<EmployeeDetailsResponse>>


    @GET("dir/employee/{directorId}/employees/withoutTasks")
    suspend fun getDirEmployeesWithoutTasks(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<EmployeeDetailsResponse>>


    @GET("emp/employee/{id}")
    suspend fun getEmployeeById(
        @Header("Authorization") token: String,
        @Path(value = "id") empId: UUID
    ): Response<EmployeeDetailsResponse>

    @GET("emp/employee/userId/{id}")
    suspend fun getEmployeeByUserId(
        @Header("Authorization") token: String,
        @Path(value = "id") userId: UUID
    ): Response<EmployeeDetailsResponse>


    @GET("emp/profile/{id}")
    suspend fun getProfileInformation(
        @Header("Authorization") token: String,
        @Path(value = "id") empId: UUID
    ): Response<EmployeeWithMetricsDetailsResponse>


    @GET("dir/employee/{directorId}/employees/search/")
    suspend fun searchDirEmployeeByName(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID,
        @Query(value = "query") query: String
    ): Response<List<EmployeeDetailsResponse>>


    @GET("admin/employee/search/")
    suspend fun searchEmployeeByName(
        @Header("Authorization") token: String,
        @Query(value = "query") query: String
    ): Response<List<EmployeeDetailsResponse>>


    @GET("dir/{directorId}/getSortedEmpByRating/")
    suspend fun sortDirEmployeesByRating(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<EmployeeDetailsResponse>>


    @GET("dir/{directorId}/employeesgetSortedEmpByWorkload/")
    suspend fun sortDirEmployeesByWorkload(
        @Header("Authorization") token: String,
        @Path(value = "directorId") directorId: UUID
    ): Response<List<EmployeeDetailsResponse>>


    @PATCH("admin/employee/")
    suspend fun updateEmployee(
        @Header("Authorization") token: String,
        @Body request: UpdateEmployeeRequest
    ): Response<EmployeeIdResponse>
}