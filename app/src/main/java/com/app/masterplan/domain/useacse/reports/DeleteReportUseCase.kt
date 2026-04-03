package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class DeleteReportUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: UUID, reportType: ReportType): Result<UUID>{
        return try {
            val deletedReportId = reportRepository.deleteReport(reportId,reportType)
            Result.success(deletedReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}