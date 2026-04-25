package com.app.masterplan.presentation.ui.tasks.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.masterplan.R
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.common.SearchSec
import com.app.masterplan.presentation.ui.tasks.components.TaskCard
import com.app.masterplan.presentation.ui.tasks.components.TaskList
import com.app.masterplan.presentation.ui.tasks.viewModel.TaskSearchScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSearchScreen(
    navController: NavHostController,
    viewModel: TaskSearchScreenViewModel = hiltViewModel(),
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val assignedTasksFlow = viewModel.assignedTasksListFlow.collectAsState()
    val searchHistoryFlow = viewModel.searchHistoryFlow.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadAssignedTasks()
    }

    val isModalVisible = viewModel.isModalVisible.collectAsState()

    val selectedTask by viewModel.selectedTask.collectAsState()

    val downloadedTask by viewModel.downloadFile.collectAsState()

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.search_by_task),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            when(assignedTasksFlow.value){
                is MasterPlanState.Failure -> {
                    showToast = true
                    errorMessage = (assignedTasksFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val tasks = (assignedTasksFlow.value as MasterPlanState.Success).result
                    SearchSec(
                        placeholder = stringResource(R.string.search_by_task),
                        onSearch = {
                            viewModel.search(it)
                        },
                        onSearchEmpty = {
                            viewModel.loadAssignedTasks()
                        },
                        searchHistoryState = searchHistoryFlow,
                        onGetSearchHistory = {
                            viewModel.getSearchHistory()
                        },
                        onSetSearchText = {
                            viewModel.search(it)
                        },
                        onClearSearchHistory = {
                            viewModel.clearSearchHistory()
                        }
                    )
                    TaskList(
                        taskList = tasks,
                        onItemClick = {
                            viewModel.openTaskTab(it)
                        }
                    )
                }
                MasterPlanState.Waiting -> null
            }
        }


        if (isModalVisible.value==true){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                onDismissRequest = {
                    viewModel.closeRequestTab()
                },
            ){
                selectedTask?.let { task ->

                    val functionButtonTitle = when(task.status){
                        TaskStatus.COMPLETED -> null
                        TaskStatus.IN_PROGRESS -> stringResource(R.string.create_report)
                        TaskStatus.NOT_STARTED -> stringResource(R.string.get_in_work)
                    }

                    TaskCard(
                        task = task,
                        onPlanClick = { navController.navigate("tasks_from_plan/${task.planId}") },
                        downloadButtonTitle = when (downloadedTask) {
                            is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                            MasterPlanState.Loading -> stringResource(R.string.loading)
                            is MasterPlanState.Success -> {
                                (downloadedTask as MasterPlanState.Success).result.absolutePath
                            }

                            MasterPlanState.Waiting -> stringResource(R.string.download_file)
                        },
                        onDownLoadButtonClick = { viewModel.downloadTask() },
                        functionButtonTitle = functionButtonTitle,
                        functionOnClick = {
                            when(task.status){
                                TaskStatus.COMPLETED -> {}
                                TaskStatus.IN_PROGRESS -> navController.navigate("create_report_task/${task.id}")
                                TaskStatus.NOT_STARTED -> viewModel.getTaskInWork()
                            }
                            viewModel.closeRequestTab()
                            viewModel.loadAssignedTasks()
                        }
                    )
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