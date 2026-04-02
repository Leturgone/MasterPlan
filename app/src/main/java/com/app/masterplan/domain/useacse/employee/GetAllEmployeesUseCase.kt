package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.Employee
import com.app.masterplan.domain.repository.remote.EmployeeRepository

class GetAllEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(): Result<List<Employee>>{
        return try {
            val list = employeeRepository.getAllEmployee()
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}