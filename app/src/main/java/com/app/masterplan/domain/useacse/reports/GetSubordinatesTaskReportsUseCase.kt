package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class GetSubordinatesTaskReportsUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(directorId: UUID): Result<List<Report>>{
        return try {
            val reports = reportRepository.getSubordinatesTaskReports(directorId)
            Result.success(reports)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}