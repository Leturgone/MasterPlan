package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.model.employee.EmployeeWithMetrics
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

// Get employee with metrics
class GetProfileInformationUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(currentEmployeeId: UUID): Result<EmployeeWithMetrics>{
        return try {
            val profileInformation = employeeRepository.getProfileInformation(currentEmployeeId)
            Result.success(profileInformation)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}