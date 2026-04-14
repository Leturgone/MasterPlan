package com.app.masterplan.presentation.ui.tasks.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.app.masterplan.domain.model.plans.Task
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.DeadLintPicker
import com.app.masterplan.presentation.ui.common.FileCard
import com.app.masterplan.presentation.ui.common.InputTextField
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.tasks.viewModel.UpdateTaskViewModel
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskScreen(
    taskId: String,
    navController: NavHostController,
    viewModel: UpdateTaskViewModel = hiltViewModel(),
) {

    val taskData: Task? by viewModel.updatedTask.collectAsState()
    val attachedDocument: MasterPlanState<AttachedDocument> by viewModel.attachedDocument.collectAsState()
    val savingState: MasterPlanState<UUID> by viewModel.savingFlow.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var endDatePickerShow by remember { mutableStateOf(false) }

    // Обработка результата выбора исполнителя из навигации
    val result: DirectorData? by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<DirectorData?>("selected_executor", null)
        ?.collectAsState(initial = null)
        ?: remember { mutableStateOf(null) }



    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            LaunchedEffect(Unit) {

                result?.let { executor ->
                    viewModel.addExecutor(executor.id)
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<DirectorData>("selected_executor")
                }
                viewModel.loadTask(taskId)
            }

            Text(
                text = stringResource(id = R.string.edit_task),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            taskData?.let {
                InputTextField(
                    value = taskData!!.title,
                    label = { Text(stringResource(R.string.task_title)) },
                    onValueChange = { viewModel.updateTitle(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                InputTextField(
                    value = taskData!!.description,
                    label = { Text(stringResource(R.string.task_desc)) },
                    onValueChange = { viewModel.updateDescription(it) },
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                )

                // Блок с выбором дедлайна
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.set_deadline),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        endDatePickerShow = true
                    }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Set date")
                    }
                }

                // Отображение выбранной даты
                Text(
                    text = convertLocalDateToDateString(taskData!!.endDate),
                    modifier = Modifier.align(Alignment.Start)
                )

                // Карточка для загрузки файла

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

                // Кнопка назначения исполнителя
                CardButton(stringResource(R.string.assign_executor)) {
                    navController.navigate("selected_executor")
                }


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
                        onClick = {viewModel.updateTask()},

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
            initialDate = taskData?.endDate
        )
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }

}