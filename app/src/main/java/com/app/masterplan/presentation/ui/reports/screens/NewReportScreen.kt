package com.app.masterplan.presentation.ui.reports.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.dto.NewReportData
import com.app.masterplan.domain.model.reports.ReportType
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.FileCard
import com.app.masterplan.presentation.ui.common.InputTextField
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.reports.viewmodel.NewReportViewModel
import java.util.UUID

@Composable
fun NewReportScreen(
    referenceId: String,
    reportType: ReportType,
    navController: NavHostController,
    viewModel: NewReportViewModel = hiltViewModel(),
) {

    val reportData: NewReportData by viewModel.currentCreatingReport.collectAsState()
    val attachedDocument: MasterPlanState<AttachedDocument> by viewModel.attachedDocument.collectAsState()
    val savingState: MasterPlanState<UUID> by viewModel.savingFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }



    Box(modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text(
                text = when(reportType){
                    ReportType.TASK -> stringResource(id = R.string.new_report_for_tasks)
                    ReportType.PLAN -> stringResource(id = R.string.new_report_for_plans)
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            InputTextField(
                value = reportData.title,
                label = { Text(stringResource(R.string.report_title)) },
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier.fillMaxWidth()
            )

            FileCard(
                fileFunc = when(attachedDocument) {
                    is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                    MasterPlanState.Loading -> stringResource(R.string.loading)
                    is MasterPlanState.Success-> {
                        val path = (attachedDocument as MasterPlanState.Success).result.file.absolutePath
                        path
                    }
                    MasterPlanState.Waiting -> stringResource(R.string.upload_file)
                },
                onFileSelected = { uri ->
                    viewModel.attachDocument(uri)
                }
            )

            InputTextField(
                value = reportData.description ?: "",
                label = { Text(stringResource(R.string.report_desc)) },
                onValueChange = { viewModel.updateDescription(it) },
                modifier = Modifier.fillMaxWidth().height(250.dp),
            )



            when(savingState){
                is MasterPlanState.Failure -> {
                    showToast = true
                    errorMessage = (savingState as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> navController.popBackStack()
                MasterPlanState.Waiting -> null
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Button(
                    onClick = {viewModel.createNewReport(
                        refId = referenceId,
                        type = reportType
                    )},

                    ) {
                    Text(text = stringResource(R.string.save))
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