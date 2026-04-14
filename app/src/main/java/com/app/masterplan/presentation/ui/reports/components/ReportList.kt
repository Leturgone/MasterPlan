package com.app.masterplan.presentation.ui.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.domain.model.reports.Report
import com.app.masterplan.presentation.ui.tasks.components.TaskListItem

@Composable
fun ReportList(
    reportList: List<ReportListDataItem>,
    onItemClick: (ReportListDataItem) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reportList.size){
            val item = reportList[it].report
            val employeeName = reportList[it].employeeName
            val employeeSurname = reportList[it].employeeSurname
            val employeePatronymic = reportList[it].employeePatronymic
            Spacer(modifier = Modifier.height(30.dp))
            ReportListItem(
                report = item,
                employeeName = employeeName,
                employeeSurname = employeeSurname,
                employeePatronymic = employeePatronymic
            ) {
                onItemClick(reportList[it])
            }
        }
    }
}