package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.util.UUID

class CreateEmployeeUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(name: String, surname: String, patronymic: String? = null,
                                directorId: UUID? = null, userId: UUID): Result<UUID>{
        return try {
            val employeeId = employeeRepository.createEmployee(
                name,surname,patronymic,directorId,userId
            )
            Result.success(employeeId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}