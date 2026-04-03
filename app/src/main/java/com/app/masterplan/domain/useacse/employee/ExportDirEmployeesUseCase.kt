package com.app.masterplan.domain.useacse.employee

import com.app.masterplan.domain.repository.remote.EmployeeRepository
import java.io.File
import java.util.UUID

class ExportDirEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(directorId: UUID): Result<File>{
        return try {
            val exportedFile = employeeRepository.exportDirEmployees(directorId)
            Result.success(exportedFile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}