package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import java.util.UUID

interface ReportRepository {

    suspend fun updateReportStatus(reportId: UUID, status: ReportStatus, type: ReportType): UUID

    suspend fun createReport(newReportData: NewReportData): UUID

    suspend fun deleteReport(reportId: UUID, reportType: ReportType): UUID
}