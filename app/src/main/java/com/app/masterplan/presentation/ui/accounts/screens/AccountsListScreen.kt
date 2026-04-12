package com.app.masterplan.presentation.ui.accounts.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ShapeDefaults
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
import com.app.masterplan.presentation.ui.accounts.components.AccountCard
import com.app.masterplan.presentation.ui.accounts.components.AccountList
import com.app.masterplan.presentation.ui.accounts.viewmodel.AccountCardViewModel
import com.app.masterplan.presentation.ui.accounts.viewmodel.AccountListViewModel
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.common.SearchSec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    navController: NavHostController,
    viewModel: AccountListViewModel = hiltViewModel(),
    modalViewModel: AccountCardViewModel = hiltViewModel(),
){
    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val accountListFlow = viewModel.accountListFlow.collectAsState()
    val searchHistoryFlow = viewModel.searchHistoryFlow.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadAccounts()
    }

    val isModalVisible = modalViewModel.isModalVisible.collectAsState()

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
                text = stringResource(id = R.string.accounts),
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
                        placeholder = stringResource(R.string.search_accounts),
                        onSearch = {
                            viewModel.search(it)
                        },
                        onSearchEmpty = {
                            viewModel.loadAccounts()
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
                            modalViewModel.openAccountTab(it)
                        }
                    )
                }
                MasterPlanState.Waiting -> null
            }
        }

        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = ShapeDefaults.Large,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 40.dp),
            onClick = {navController.navigate("new_profile")}
        ) {
            Icon(imageVector =
                Icons.Default.Add,
                contentDescription = "addProfileButton",
                modifier = Modifier.size(20.dp))

        }

        if (isModalVisible.value){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                onDismissRequest = {
                    modalViewModel.closeRequestTab()
                },
            ){
                AccountCard(modalViewModel) {
                    val employeeId = modalViewModel.employeeFlow.value?.id?:""
                    navController.navigate("edit_profile/${employeeId}")
                }
            }
        }
    }
}