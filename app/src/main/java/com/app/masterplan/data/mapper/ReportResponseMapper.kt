package com.app.masterplan.data.mapper

import com.app.masterplan.data.api.reportsApi.dto.responce.ReportIdResponse
import com.app.masterplan.data.api.reportsApi.dto.responce.ReportResponse
import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.domain.model.reports.ReportStatus
import com.app.masterplan.domain.model.reports.ReportType

object ReportResponseMapper {

    fun toDomain(response: ReportIdResponse) = response.id

    fun toDomain(response: ReportResponse): Report {

        val status = try {
            ReportStatus.valueOf(response.reportStatus)
        }catch (_: Exception){
            throw Exception("Invalid status: ${response.reportStatus}")
        }

        val type = try {
            ReportType.valueOf(response.type)
        }catch (_: Exception){
            throw Exception("Invalid type: ${response.reportStatus}")
        }

        return Report(
            id = response.id,
            title = response.title,
            creationDate = DateSerializer.toLocalDateTime(response.creationDate),
            editDate = response.editDate?.let { DateSerializer.toLocalDateTime(it)},
            description = response.description,
            reportStatus = status,
            employeeId = response.employeeId,
            referenceId = response.referenceId,
            type = type,
            documentId = response.documentId
        )
    }

    fun toDomain(response: List<ReportResponse>): List<Report> {
        return response.map { toDomain(it) }
    }

}