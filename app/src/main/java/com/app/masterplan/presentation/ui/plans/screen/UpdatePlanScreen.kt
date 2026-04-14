package com.app.masterplan.presentation.ui.plans.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.app.masterplan.domain.dto.AttachedDocument
import com.app.masterplan.domain.model.plans.Plan
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.DeadLintPicker
import com.app.masterplan.presentation.ui.common.FileCard
import com.app.masterplan.presentation.ui.common.InputTextField
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.plans.viewmodel.UpdatePlanViewModel
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePlanScreen(
    planId: String,
    navController: NavHostController,
    viewModel: UpdatePlanViewModel = hiltViewModel(),
) {

    val planData: Plan? by viewModel.updatedPlan.collectAsState()
    val attachedDocument: MasterPlanState<AttachedDocument> by viewModel.attachedDocument.collectAsState()
    val savingState: MasterPlanState<UUID> by viewModel.savingFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var endDatePickerShow by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            LaunchedEffect(Unit) {
                viewModel.loadPlan(planId)
            }

            planData?.let {
                Text(
                    text = stringResource(id = R.string.edit_plan),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )

                InputTextField(
                    value = planData!!.title,
                    label = { Text(stringResource(R.string.plan_title)) },
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

                // Блок с выбором дедлайна
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(

                    ) {
                        Text(
                            stringResource(R.string.set_deadline),
                            fontWeight = FontWeight.Bold
                        )

                        FloatingActionButton(
                            content =  {
                                Icon(Icons.Default.CalendarToday, contentDescription = "Set date")
                            },
                            onClick = {
                                endDatePickerShow = true
                            },
                            modifier = Modifier
                                .padding(start =20.dp),
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Отображение выбранной даты
                    Text(
                        text = convertLocalDateToDateString(planData!!.endDate),
                    )
                }
                InputTextField(
                    value = planData!!.description,
                    label = { Text(stringResource(R.string.plan_desc)) },
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
                        onClick = {viewModel.updatePlan()},

                        ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }

        }


        DeadLintPicker(
            isVisible = endDatePickerShow,
            onDismiss = {
                endDatePickerShow = false
            },
            onDateSelected = {
                viewModel.updateDate(it)
            },
            initialDate = planData?.endDate
        )
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }

}