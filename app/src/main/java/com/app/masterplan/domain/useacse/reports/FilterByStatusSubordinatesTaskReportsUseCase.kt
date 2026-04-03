package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class FilterByStatusSubordinatesTaskReportsUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(directorId: UUID, status: ReportStatus): Result<List<Report>>{
        return try {
            val reports = reportRepository.getFilterByStatusSubordinatesTaskReports(
                directorId,status
            )
            Result.success(reports)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}