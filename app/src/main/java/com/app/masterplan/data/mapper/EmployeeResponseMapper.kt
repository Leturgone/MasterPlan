package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.employeeApi.dto.responce.DirectorDetailsDto
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeDetailsResponse
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeIdResponse
import com.app.masterplan.data.api.employeeApi.dto.responce.EmployeeWithMetricsDetailsResponse
import com.app.masterplan.domain.model.employee.DirectorDetails
import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics

object EmployeeResponseMapper {

    fun toDomain(resp: EmployeeIdResponse) = resp.id


    fun toDomain(resp: EmployeeDetailsResponse): Employee{
        return Employee(
            id = resp.id,
            name = resp.name,
            surname = resp.surname,
            patronymic = resp.patronymic,
            directorId = resp.directorId,
            userId = resp.userId
        )
    }

    fun toDomain(resp: List<EmployeeDetailsResponse>): List<Employee>{
        return resp.map { toDomain(it) }
    }

    fun toDomain(resp: EmployeeWithMetricsDetailsResponse): EmployeeWithMetrics{
        val director = toDomain(resp.director)
        return EmployeeWithMetrics(
            id = resp.id,
            name = resp.name,
            surname = resp.surname,
            patronymic = resp.patronymic,
            director = director,
            rating = resp.metrics.rating,
            workload = resp.metrics.workload,
            assignedTasksCount = resp.metrics.assignedTasksCount
        )
    }

    private fun toDomain(dto:DirectorDetailsDto?): DirectorDetails?{
        return dto?.let { dir ->
            DirectorDetails(
                name = dir.name,
                surname = dir.surname,
                patronymic = dir.patronymic
            )
        }
    }

}