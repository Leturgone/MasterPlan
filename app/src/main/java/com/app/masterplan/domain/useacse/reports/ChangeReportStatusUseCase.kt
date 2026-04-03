package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class ChangeReportStatusUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: UUID, status: ReportStatus, type: ReportType): Result<UUID>{
        return try {
            val reportWithUpdatedStatusId = reportRepository.updateReportStatus(reportId,status,type)
            Result.success(reportWithUpdatedStatusId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}