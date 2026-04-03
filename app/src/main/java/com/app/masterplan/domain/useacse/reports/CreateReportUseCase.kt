package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class CreateReportUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(newReportData: NewReportData): Result<UUID>{
        return try {
            val createdReportId = reportRepository.createReport(newReportData)
            Result.success(createdReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}