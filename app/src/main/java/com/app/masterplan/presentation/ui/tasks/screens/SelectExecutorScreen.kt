package com.app.masterplan.presentation.ui.tasks.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.app.masterplan.presentation.ui.accounts.components.AccountList
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.common.SearchSec
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeListScreenViewModel

@Composable
fun SelectExecutorScreen(
    navController: NavHostController,
    viewModel: EmployeeListScreenViewModel  = hiltViewModel(),
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val accountListFlow = viewModel.employeesFlow.collectAsState()
    val searchHistoryFlow = viewModel.searchHistoryFlow.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadDirEmployees()
    }

    Box() {
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.assign_executor),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            when(accountListFlow.value){
                is MasterPlanState.Failure -> {
                    showToast = true
                    errorMessage = (accountListFlow.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val accounts = (accountListFlow.value as MasterPlanState.Success).result
                    SearchSec(
                        placeholder = stringResource(R.string.search_emp),
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
                    AccountList(
                        accountsList = accounts,
                        onItemClick = {
                            val dirData = DirectorData(
                                id = it.id,
                                name = it.name,
                                surname = it.surname,
                                patronymic = it.patronymic,
                                directorId = it.directorId,
                                userId = it.userId
                            )
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "selected_executor", dirData
                            )
                            navController.popBackStack()
                        }
                    )
                }
                MasterPlanState.Waiting -> null
            }
        }
    }
}