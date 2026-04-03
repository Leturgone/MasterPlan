package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import java.io.File
import java.util.UUID

interface EmployeeRepository {
    suspend fun createEmployee(name: String, surname: String, patronymic: String? = null,
                               directorId: UUID? = null, userId: UUID): UUID

    suspend fun exportDirEmployees(directorId: UUID): File

    suspend fun getAllDirectorEmployees(directorId: UUID): List<Employee>

    suspend fun getAllEmployee(): List<Employee>

    suspend fun  getDirEmployeesWithoutTasks(directorId: UUID): List<Employee>

    suspend fun getEmployeeById(employeeId: UUID): Employee

    suspend fun getProfileInformation(currentEmployeeId: UUID): EmployeeWithMetrics

    suspend fun searchDirEmployeeByName(query: String, directorId: UUID): List<Employee>

    suspend fun searchEmployeeByName(query: String): List<Employee>

    suspend fun sortDirEmployeesByRating(directorId: UUID): List<Employee>

    suspend fun sortDirEmployeesByWorkload(directorId: UUID): List<Employee>

    suspend fun updateEmployee(employeeId: UUID, newEmployee: Employee): UUID

}