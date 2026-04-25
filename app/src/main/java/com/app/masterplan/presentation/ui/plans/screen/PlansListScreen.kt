package com.app.masterplan.presentation.ui.plans.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
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
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.FabMenu
import com.app.masterplan.presentation.ui.common.FabMenuOption
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.plans.components.PlanCard
import com.app.masterplan.presentation.ui.plans.components.PlanList
import com.app.masterplan.presentation.ui.plans.viewmodel.PlansListScreenViewModel
import com.app.masterplan.presentation.ui.tasks.components.TaskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlansListScreen(
    navController: NavHostController,
    viewModel: PlansListScreenViewModel = hiltViewModel()
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val dirPlansFlow = viewModel.dirPlansListFlow.collectAsState()

    val menuOptions = listOf(
        FabMenuOption(
            title = stringResource(R.string.all),
            icon = Icons.Default.Menu,
            onClick = { viewModel.loadPlans() }
        ),
        FabMenuOption(
            title = stringResource(R.string.completed),
            icon = Icons.Default.Check,
            onClick = { viewModel.getCompletedPlans() }
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
        viewModel.loadPlans()
    }

    val isModalVisible = viewModel.isModalVisible.collectAsState()

    val selectedPlan by viewModel.selectedPlan.collectAsState()

    val downloadedPlan by viewModel.downloadFile.collectAsState()

    val currentTab = viewModel.currentTab.collectAsState()


    val isCrud by viewModel.isCrudOperations.collectAsState()


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
                    text = stringResource(id = R.string.plans_long),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )
            }


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



            when(dirPlansFlow.value){
                is MasterPlanState.Failure -> LaunchedEffect(dirPlansFlow.value){
                    showToast = true
                    errorMessage = (dirPlansFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val plans = (dirPlansFlow.value as MasterPlanState.Success).result
                    PlanList(plans) {
                        viewModel.openPlanTab(it)
                    }
                }
                MasterPlanState.Waiting -> null
            }
        }

        FloatingActionButton(
            content =  {
                Icon(Icons.Default.Add, null)
            },
            onClick = {
                navController.navigate("create_plan")
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
                selectedPlan?.let { plan ->

                    PlanCard(
                        plan = plan,
                        downloadButtonTitle = when (downloadedPlan) {
                            is MasterPlanState.Failure -> stringResource(R.string.error_while_loading)
                            MasterPlanState.Loading -> stringResource(R.string.loading)
                            is MasterPlanState.Success -> {
                                (downloadedPlan as MasterPlanState.Success).result.absolutePath
                            }

                            MasterPlanState.Waiting -> stringResource(R.string.download_file)
                        },
                        onDownloadFileClick = { viewModel.downloadPlan() },
                        onTasksClick = {
                            navController.navigate("tasks_from_plan/${plan.id}")
                            viewModel.closeRequestTab()
                        },
                        crud = isCrud,
                        onDeleteClick = {
                            viewModel.deletePlan()
                        },
                        onEditClick = {
                            navController.navigate("edit_plan/${plan.id}")
                            viewModel.closeRequestTab()
                        },
                        onStartClick = {
                            viewModel.getPlanInWork()
                        },
                        onCreateReportClick = {
                            navController.navigate("create_report_plan/${plan.id}")
                            viewModel.closeRequestTab()
                        },
                        onCompleteClick = {
                            viewModel.completePlan()
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