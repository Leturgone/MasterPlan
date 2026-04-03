package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class FilterByStatusCreatedReportsUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(employeeId: UUID, status: ReportStatus, reportType: ReportType): Result<List<Report>>{
        return try {
            val reports = reportRepository.getFilterByStatusCreatedReports(
                employeeId,status,reportType
            )
            Result.success(reports)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}