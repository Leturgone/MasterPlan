package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class GetReportInfUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: UUID, reportType: ReportType): Result<Report>{
        return try {
            val report = reportRepository.getReport(reportId,reportType)
            Result.success(report)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}