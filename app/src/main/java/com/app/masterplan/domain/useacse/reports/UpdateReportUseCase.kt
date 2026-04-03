package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.UpdateReportData
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class UpdateReportUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: UUID, updatedData: UpdateReportData,
                                reportType: ReportType, document: AttachedDocument): Result<UUID>{
        return try {
            val updatedReportId = reportRepository.updateReport(
                reportId,updatedData,reportType,document
            )
            Result.success(updatedReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}