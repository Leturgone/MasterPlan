package com.app.masterplan.data.repository

import com.app.masterplan.data.api.employeeApi.EmployeeApi
import com.app.masterplan.data.api.employeeApi.dto.request.CreateEmployeeRequest
import com.app.masterplan.data.api.employeeApi.dto.request.UpdateEmployeeRequest
import com.app.masterplan.data.api.exception.ApiErrorResponse
import com.app.masterplan.data.exception.ApiException
import com.app.masterplan.data.mapper.ApiErrorResponseHandler
import com.app.masterplan.data.mapper.EmployeeResponseMapper
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.data.storage.TokenDataStorage
import java.io.File
import java.util.UUID
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeApi: EmployeeApi,
    private val tokenStorage: TokenDataStorage,
    private val localFileDataSource: LocalFileDataStorage,
): EmployeeRepository {
    override suspend fun createEmployee(
        name: String,
        surname: String,
        patronymic: String?,
        directorId: UUID?,
        userId: UUID
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage()

        val request = CreateEmployeeRequest(
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId,
        )
        val response = employeeApi.createEmployee(token.token,request)

        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun exportDirEmployees(directorId: UUID): File {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.exportDirEmployees(token.token,directorId)
        val bytes = ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        val file = localFileDataSource.saveFileToDataStorage(bytes)
        return file
    }

    override suspend fun getAllDirectorEmployees(directorId: UUID): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.getDirEmployees(token.token,directorId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getAllEmployee(): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.getAllEmployees(token.token)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getDirEmployeesWithoutTasks(directorId: UUID): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.getDirEmployeesWithoutTasks(token.token,directorId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getEmployeeById(employeeId: UUID): Employee {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.getEmployeeById(token.token,employeeId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun getProfileInformation(currentEmployeeId: UUID): EmployeeWithMetrics {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.getProfileInformation(token.token,currentEmployeeId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun searchDirEmployeeByName(query: String, directorId: UUID): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.searchDirEmployeeByName(
            token = token.token,
            directorId = directorId,
            query = query
        )
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun searchEmployeeByName(query: String): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response =employeeApi.searchEmployeeByName(token.token,query)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun sortDirEmployeesByRating(directorId: UUID): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.sortDirEmployeesByRating(token.token,directorId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun sortDirEmployeesByWorkload(directorId: UUID): List<Employee> {
        val token = tokenStorage.getTokenFromDataStorage()
        val response = employeeApi.sortDirEmployeesByWorkload(token.token,directorId)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }

    override suspend fun updateEmployee(
        employeeId: UUID,
        newEmployee: Employee
    ): UUID {
        val token = tokenStorage.getTokenFromDataStorage()
        val request = UpdateEmployeeRequest(
            id = newEmployee.id,
            newName = newEmployee.name,
            newSurname = newEmployee.surname,
            newPatronymic = newEmployee.patronymic,
            newDirectorId = newEmployee.directorId,
            userId = newEmployee.userId
        )
        val response = employeeApi.updateEmployee(token.token,request)
        return EmployeeResponseMapper.toDomain(
            ApiErrorResponseHandler.handleResponse(response,::errorMapper)
        )
    }


    private fun errorMapper(errorResp: ApiErrorResponse): ApiException.EmployeeApiException {
        return ApiException.EmployeeApiException(
            status = errorResp.status,
            apiMessage = errorResp.message,
        )
    }

}