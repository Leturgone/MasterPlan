package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class GetEmployeeByIdUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: UUID): Result<Employee>{
        return try {
            val employee = employeeRepository.getEmployeeById(employeeId)
            Result.success(employee)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}