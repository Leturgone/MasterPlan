package com.app.masterplan.presentation.ui.reports.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.masterplan.R
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.FabMenu
import com.app.masterplan.presentation.ui.common.FabMenuOption
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.reports.components.AboutCreatedReportCard
import com.app.masterplan.presentation.ui.reports.components.AboutToCheckReport
import com.app.masterplan.presentation.ui.reports.components.ReportList
import com.app.masterplan.presentation.ui.reports.viewmodel.ReportsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportListScreen(
    navController: NavHostController,
    viewModel: ReportsScreenViewModel = hiltViewModel()
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val assignedReportsFlow = viewModel.reportsListFlow.collectAsState()

    val menuOptions = listOf(
        FabMenuOption(
            title = stringResource(R.string.all),
            icon = Icons.Default.Menu,
            onClick = { viewModel.loadAllReports() }
        ),
        FabMenuOption(
            title = stringResource(R.string.checked),
            icon = Icons.Default.Check,
            onClick = { viewModel.getCheckedReports() }
        ),
        FabMenuOption(
            title = stringResource(R.string.checking),
            icon = Icons.Default.Edit,
            onClick = { viewModel.getInCheckReports() }
        ),
        FabMenuOption(
            title = stringResource(R.string.not_check),
            icon = Icons.Default.Error,
            onClick = { viewModel.getNotCheckedReports() }
        ),
        FabMenuOption(
            title = stringResource(R.string.to_update),
            icon = Icons.Default.Autorenew,
            onClick = { viewModel.getToUpdateReports() }
        )

    )

    LaunchedEffect(Unit) {
        viewModel.loadAllReports()
    }

    val isModalVisible = viewModel.isModalVisible.collectAsState()

    val selectedReport by viewModel.selectedReport.collectAsState()

    val downloadedTask by viewModel.downloadFile.collectAsState()

    val currentTab = viewModel.currentTab.collectAsState()

    val isSwitchOn by viewModel.isSwitchOn.collectAsState()

    val reportItemTitle by viewModel.reportItemTitle.collectAsState()

    val isSwitchVisible by viewModel.isSwitchVisible.collectAsState()


    Box() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text =
                        when(isSwitchVisible){
                            true -> when(isSwitchOn){
                                true -> stringResource(id = R.string.report_for_tasks)
                                false -> stringResource(id = R.string.report_for_plans)
                            }
                            false -> stringResource(id = R.string.report_for_tasks)
                        },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )
                if (isSwitchVisible){
                    Switch(checked = isSwitchOn,
                        onCheckedChange = {
                            viewModel.updateSwitcher()
                        },
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                    )
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                val options = listOf(
                    stringResource(R.string.my),
                    stringResource(R.string.to_check)
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                viewModel.updateTab(index)
                            },
                            selected = index == currentTab.value,
                            label = { Text(label) },
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary,
                                activeContentColor = MaterialTheme.colorScheme.onPrimary,
                                activeBorderColor = MaterialTheme.colorScheme.primary,

                                inactiveContainerColor = MaterialTheme.colorScheme.onPrimary,
                                inactiveContentColor = MaterialTheme.colorScheme.primary,
                                inactiveBorderColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        )
                    }
                }
            }




            when(assignedReportsFlow.value){
                is MasterPlanState.Failure -> LaunchedEffect(assignedReportsFlow.value){
                    showToast = true
                    errorMessage = (assignedReportsFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val reports = (assignedReportsFlow.value as MasterPlanState.Success).result
                    ReportList(reports) {
                        viewModel.openReportTab(it)
                    }
                }
                MasterPlanState.Waiting -> null
            }
        }

        FabMenu(
            menuOptions = menuOptions
        )



        if (isModalVisible.value==true){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                onDismissRequest = {
                    viewModel.closeRequestTab()
                },
            ){
                selectedReport?.let { report ->

                    when(currentTab.value){
                        0 -> {
                            AboutCreatedReportCard(
                                report = report.report,
                                onDeleteClick = {
                                    viewModel.deleteReport()
                                    viewModel.closeRequestTab()
                                    viewModel.loadAllReports()
                                                },
                                referenceTitle = when (reportItemTitle){
                                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                                    MasterPlanState.Loading -> stringResource(R.string.loading)
                                    is MasterPlanState.Success -> {
                                        (reportItemTitle as MasterPlanState.Success).result
                                    }
                                    MasterPlanState.Waiting -> stringResource(R.string.loading)
                                },
                                downloadButtonTitle = when (downloadedTask) {
                                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                                    MasterPlanState.Loading -> stringResource(R.string.loading)
                                    is MasterPlanState.Success -> {
                                        (downloadedTask as MasterPlanState.Success).result.absolutePath
                                    }
                                    MasterPlanState.Waiting -> stringResource(R.string.download_report)
                                },
                                onDownloadClick = { viewModel.downloadReport() },
                                onUpdateClick = {
                                    when(report.report.type){
                                        ReportType.TASK -> navController.navigate("update_report_task/${report.report.id}")
                                        ReportType.PLAN -> navController.navigate("update_report_plan/${report.report.id}")
                                    }
                                    viewModel.closeRequestTab()
                                    viewModel.loadAllReports()
                                }
                            )
                        }
                        1 -> {

                            AboutToCheckReport(
                                report = report.report,
                                employeeName = report.employeeName,
                                employeeSurname = report.employeeSurname,
                                employeePatronymic = report.employeePatronymic,
                                referenceTitle = when (reportItemTitle){
                                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                                    MasterPlanState.Loading -> stringResource(R.string.loading)
                                    is MasterPlanState.Success -> {
                                        (reportItemTitle as MasterPlanState.Success).result
                                    }
                                    MasterPlanState.Waiting -> stringResource(R.string.loading)
                                },
                                downloadButtonTitle = when (downloadedTask) {
                                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                                    MasterPlanState.Loading -> stringResource(R.string.loading)
                                    is MasterPlanState.Success -> {
                                        (downloadedTask as MasterPlanState.Success).result.absolutePath
                                    }
                                    MasterPlanState.Waiting -> stringResource(R.string.download_report)
                                },
                                onDownloadClick = { viewModel.downloadReport() },
                                onChecking = { viewModel.setReportInChecking()},
                                onToUpdate = { viewModel.setReportToUpdate()},
                                onCheck = {viewModel.setReportInCheck()},
                            )
                        }
                    }
                }

            }
        }
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }
}