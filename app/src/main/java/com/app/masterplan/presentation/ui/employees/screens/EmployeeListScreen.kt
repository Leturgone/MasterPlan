package com.app.masterplan.presentation.ui.employees.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.FabMenu
import com.app.masterplan.presentation.ui.common.FabMenuOption
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.common.SearchSec
import com.app.masterplan.presentation.ui.employees.components.EmployeeCard
import com.app.masterplan.presentation.ui.employees.components.EmployeeList
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeCardViewModel
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeListScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListScreen(
    viewModel: EmployeeListScreenViewModel = hiltViewModel(),
    modalViewModel: EmployeeCardViewModel = hiltViewModel(),
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    val employeesFlow = viewModel.employeesFlow.collectAsState()
    val searchHistoryFlow = viewModel.searchHistoryFlow.collectAsState()
    val exportedFile by viewModel.downloadExport.collectAsState()


    val menuOptions = listOf(
        FabMenuOption(
            title = stringResource(R.string.all),
            icon = Icons.Default.Menu,
            onClick = { viewModel.loadDirEmployees() }
        ),
        FabMenuOption(
            title = stringResource(R.string.by_rating),
            icon = Icons.Default.Check,
            onClick = { viewModel.sortDirEmployeesByRating() }
        ),
        FabMenuOption(
            title = stringResource(R.string.by_workload),
            icon = Icons.Default.Edit,
            onClick = { viewModel.sortDirEmployeesByWorkload() }
        ),
        FabMenuOption(
            title = stringResource(R.string.without_tasks),
            icon = Icons.Default.Error,
            onClick = { viewModel.getDirEmployeesWithoutTasks() }
        )
    )


    LaunchedEffect(Unit) {
        viewModel.loadDirEmployees()
    }

    val isModalVisible = modalViewModel.isModalVisible.collectAsState()

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
                    text = stringResource(id = R.string.employees),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )

                when(exportedFile){
                    is MasterPlanState.Failure -> LaunchedEffect(employeesFlow.value){
                        showToast = true
                        errorMessage = (exportedFile as MasterPlanState.Failure).exception.message.toString()
                    }
                    is MasterPlanState.Success -> LaunchedEffect(employeesFlow.value){
                        showToast = true
                        errorMessage = "Скачано ${(exportedFile as MasterPlanState.Success).result.absolutePath}"
                    }
                    else -> null
                }
                ExtendedFloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        viewModel.exportDirEmployees()
                    },
                    icon = { Icon(Icons.Default.Upload, "Export employees button") },
                    text = {
                        Text(
                            text = stringResource(id = R.string.export),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }


            when(employeesFlow.value){
                is MasterPlanState.Failure -> LaunchedEffect(employeesFlow.value){
                    showToast = true
                    errorMessage = (employeesFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val accounts = (employeesFlow.value as MasterPlanState.Success).result
                    SearchSec(
                        placeholder = stringResource(R.string.search_accounts),
                        onSearch = {
                            viewModel.search(it)
                        },
                        onSearchEmpty = {
                            viewModel.loadDirEmployees()
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
                    EmployeeList(
                        employeeList = accounts,
                        onItemClick = {
                            modalViewModel.openEmployeeTab(it.id)
                        }
                    )
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
                    modalViewModel.closeRequestTab()
                },
            ){
                EmployeeCard(modalViewModel)
            }
        }
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }
}