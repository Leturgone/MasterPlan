package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class GetDirEmployeesWithoutTasksUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(directorId: UUID): Result<List<Employee>>{
        return try {
            val list = employeeRepository.getDirEmployeesWithoutTasks(directorId)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}