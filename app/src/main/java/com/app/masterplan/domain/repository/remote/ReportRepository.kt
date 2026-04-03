package com.app.masterplan.domain.repository.remote

import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.dto.UpdateReportData
import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType
import java.util.UUID

interface ReportRepository {

    suspend fun updateReportStatus(reportId: UUID, status: ReportStatus, type: ReportType): UUID

    suspend fun createReport(newReportData: NewReportData, document: AttachedDocument, reportType: ReportType): UUID

    suspend fun deleteReport(reportId: UUID, reportType: ReportType): UUID

    suspend fun getFilterByStatusCreatedReports(employeeId: UUID,
                                                status: ReportStatus,
                                                reportType: ReportType): List<Report>

    suspend fun getFilterByStatusSubordinatesTaskReports(directorId: UUID,
                                                         status: ReportStatus): List<Report>

    suspend fun getCreatedReports(employeeId: UUID, reportType: ReportType): List<Report>

    suspend fun getReport(reportId: UUID, reportType: ReportType): Report

    suspend fun getSubordinatesTaskReports(directorId: UUID): List<Report>

    suspend fun updateReport(reportId: UUID, updatedData: UpdateReportData,
                             reportType: ReportType, document: AttachedDocument): UUID
}