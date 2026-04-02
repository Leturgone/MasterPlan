package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class SearchDirEmployeeByNameUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(query: String, directorId: UUID): Result<List<Employee>>{
        return try {
            val employeeList = employeeRepository.searchDirEmployeeByName(query,directorId)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}