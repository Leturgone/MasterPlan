package com.app.masterplan.domain.useacse.reports

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.domain.repository.remote.ReportRepository
import java.util.UUID

class CreateReportUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(newReportData: NewReportData,
                                document: AttachedDocument,
                                reportType: ReportType
                                ): Result<UUID>{
        return try {
            val createdReportId = reportRepository.createReport(newReportData,document,reportType)
            Result.success(createdReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}