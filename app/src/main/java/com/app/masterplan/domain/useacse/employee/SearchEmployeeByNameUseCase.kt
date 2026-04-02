package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository

class SearchEmployeeByNameUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(query: String): Result<List<Employee>>{
        return try {
            val employeeList = employeeRepository.searchEmployeeByName(query)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}