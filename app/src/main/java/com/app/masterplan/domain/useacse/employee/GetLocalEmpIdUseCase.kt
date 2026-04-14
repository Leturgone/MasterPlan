package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class GetLocalEmpIdUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(): Result<UUID>{
        return try {
            val id = employeeRepository.getLocalEmployeeId()
            Result.success(id)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}