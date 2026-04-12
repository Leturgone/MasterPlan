package com.app.masterplan.presentation.ui.accounts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.app.masterplan.presentation.ui.accounts.components.AccountDataEditComponent
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.accounts.components.EmployeeDataEditComponent
import com.app.masterplan.presentation.ui.accounts.viewmodel.CreateAccountScreenViewModel
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState

@Composable
fun CreateAccountScreen(
    navController: NavHostController,
    viewModel: CreateAccountScreenViewModel = hiltViewModel()
){

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val currentTab = viewModel.currentTab.collectAsState()
    val profileData by viewModel.accountData.collectAsState()
    val roles by viewModel.roles.collectAsState()

    val selectedDir by viewModel.selectedDir.collectAsState()
    val savingFlow = viewModel.savingFlow.collectAsState()

    val result: DirectorData? by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<DirectorData?>("selected_director", null)
        ?.collectAsState(initial = null)
        ?: remember { mutableStateOf(null) }


    LaunchedEffect(result) {
        result?.let { director ->
            viewModel.updateDirector(director)
            // Очищаем временный результат после обработки
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<DirectorData>("selected_director")
        }
    }


    Box(modifier = Modifier.fillMaxWidth()) {


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.create_acc),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )


            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                var selectedIndex by remember { mutableIntStateOf(0) }
                val options = listOf(
                    stringResource(R.string.account_data),
                    stringResource(R.string.employee_data)
                )

                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { viewModel.setCurrentTab(index) },
                            selected = index == selectedIndex,
                            label = { Text(label) }
                        )
                    }
                }

                when (currentTab.value){
                    0 -> {
                        AccountDataEditComponent(
                            profileData = profileData,
                            roles = roles,
                            onLoginChange = { viewModel.updateLogin(it) },
                            onPasswordChange = {viewModel.updatePassword(it)},
                            onRoleToggle = {viewModel.toggleRoleSelection(it)}
                        )
                    }
                    1 -> {
                        EmployeeDataEditComponent(
                            profileData = profileData,
                            onNameChange = { viewModel.updateName(it) },
                            onSurnameChange = { viewModel.updateSurname(it) },
                            onPatronymicChange = { viewModel.updatePatronymic(it) },
                            directorData = selectedDir,
                            onOpenDirectorListClick = {
                                navController.navigate("director_selection")
                            }
                        )
                    }
                }


                when(savingFlow.value){
                    is MasterPlanState.Failure -> LaunchedEffect(savingFlow.value) {
                        showToast = true
                        errorMessage = (savingFlow.value as MasterPlanState.Failure).exception.toString()
                    }
                    MasterPlanState.Loading -> CircularProgressIndicator()
                    is MasterPlanState.Success -> LaunchedEffect(Unit){
                        navController.popBackStack()
                    }
                    MasterPlanState.Waiting -> null
                }

                Button(
                    onClick = {
                        viewModel.saveProfile() },
                ) {
                    Text(text = stringResource(id = R.string.save),)
                }

                Spacer(modifier = Modifier.height(1.dp))
            }

            CustomToastMessage(
                message = errorMessage,
                isVisible = showToast,
                onDismiss = { showToast = false },
            )
        }

    }


}