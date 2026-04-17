package com.app.masterplan.presentation.ui.tasks.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.app.masterplan.domain.model.plans.TaskStatus
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.FabMenu
import com.app.masterplan.presentation.ui.common.FabMenuOption
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.tasks.components.TaskCard
import com.app.masterplan.presentation.ui.tasks.components.TaskList
import com.app.masterplan.presentation.ui.tasks.viewModel.AssignedTasksScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignedTasksScreen(
    navController: NavHostController,
    viewModel: AssignedTasksScreenViewModel = hiltViewModel()
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val assignedTasksFlow = viewModel.assignedTasksListFlow.collectAsState()

    val menuOptions = listOf(
        FabMenuOption(
            title = stringResource(R.string.all),
            icon = Icons.Default.Menu,
            onClick = { viewModel.loadAssignedTasks() }
        ),
        FabMenuOption(
            title = stringResource(R.string.completed),
            icon = Icons.Default.Check,
            onClick = { viewModel.getCompletedTasks() }
        ),
        FabMenuOption(
            title = stringResource(R.string.inProgress),
            icon = Icons.Default.Edit,
            onClick = { viewModel.getInProgressTasks() }
        ),
        FabMenuOption(
            title = stringResource(R.string.not_started),
            icon = Icons.Default.Error,
            onClick = { viewModel.getNotStartedTasks() }
        )
    )

    LaunchedEffect(Unit) {
        viewModel.loadAssignedTasks()
    }

    val isModalVisible = viewModel.isModalVisible.collectAsState()

    val selectedTask by viewModel.selectedTask.collectAsState()

    val downloadedTask by viewModel.downloadFile.collectAsState()

    val currentTab = viewModel.currentTab.collectAsState()


    Box() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.assigned_tasks),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                val options = listOf(
                    stringResource(R.string.by_new),
                    stringResource(R.string.by_time)
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
                                viewModel.loadByCurrentSort()
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





            when(assignedTasksFlow.value){
                is MasterPlanState.Failure -> LaunchedEffect(assignedTasksFlow.value){
                    showToast = true
                    errorMessage = (assignedTasksFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val tasks = (assignedTasksFlow.value as MasterPlanState.Success).result
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

        FloatingActionButton(
            shape = CircleShape,
            content =  {
                Icon(Icons.Default.Search, null)
            },
            onClick = {
                navController.navigate("search_assigned_tasks")
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start =20.dp ,bottom = 50.dp),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )

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
                selectedTask?.let { task ->

                    val functionButtonTitle = when(task.status){
                        TaskStatus.COMPLETED -> null
                        TaskStatus.IN_PROGRESS -> stringResource(R.string.create_new_report)
                        TaskStatus.NOT_STARTED -> stringResource(R.string.get_in_work)
                    }

                    TaskCard(
                        task = task,
                        onPlanClick = { navController.navigate("plan/${task.planId}/tasks") },
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