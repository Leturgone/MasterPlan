package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class UpdateEmployeeUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: UUID, newEmployee: Employee): Result<UUID>{
        return try {
            val updatedEmployeeId = employeeRepository.updateEmployee(
                employeeId = employeeId,
                newEmployee = newEmployee
            )
            Result.success(updatedEmployeeId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}