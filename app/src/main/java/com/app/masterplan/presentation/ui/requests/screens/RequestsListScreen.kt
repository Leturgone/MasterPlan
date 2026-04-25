package com.app.masterplan.presentation.ui.requests.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.masterplan.R
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.requests.components.RequestsCard
import com.app.masterplan.presentation.ui.requests.components.RequestsListItemCard
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestCardViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestsListScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsListScreen(
    navController: NavHostController,
    viewModel: RequestsListScreenViewModel = hiltViewModel(),
    modalViewModel: RequestCardViewModel = hiltViewModel(),
){



    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val requestsList = viewModel.requestListFlow.collectAsState()
    val showButton = viewModel.showAddButton.collectAsState()

    val isModalVisible = modalViewModel.isModalVisible.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRequestsList()
        modalViewModel.closeRequestTab()

    }


    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.requests),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            when(requestsList.value){
                is MasterPlanState.Failure -> {
                    LaunchedEffect(Unit) {
                        showToast = true
                        errorMessage = (requestsList.value as MasterPlanState.Failure).exception.message ?: ""
                    }
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val requestsList = (requestsList.value as MasterPlanState.Success).result
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) { 
                        items(requestsList.size){
                            val item = requestsList[it]
                            Spacer(modifier = Modifier.height(30.dp))
                            RequestsListItemCard(item) {
                                modalViewModel.openRequestTab(request = item)
                            }
                        }
                    }
                }
                MasterPlanState.Waiting -> null
            }
        }

        if (showButton.value){
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = ShapeDefaults.Large,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 50.dp, end = 40.dp),
                onClick = {navController.navigate("new_request")}
            ) {
                Icon(imageVector =
                    Icons.Default.Add,
                    contentDescription = "addButton",
                    modifier = Modifier.size(20.dp))

            }
        }

        if (isModalVisible.value){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                onDismissRequest = {
                    modalViewModel.closeRequestTab()
                },
            ){
                RequestsCard(
                    modalViewModel,
                    createAnswerButtonOnClick = {
                        val requestId = modalViewModel.selectedRequest.value?.id ?: ""
                        navController.navigate("new_answer/${requestId}")
                    },
                    onGetInWork = {
                        viewModel.loadRequestsList()
                    }
                )
            }
        }
        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }

}