package com.app.masterplan.presentation.ui.reports.components

import com.app.masterplan.domain.model.reports.Report

data class ReportListDataItem(
    val report: Report,
    val employeeName: String? = null,
    val employeeSurname: String? = null,
    val employeePatronymic: String? = null,
)
