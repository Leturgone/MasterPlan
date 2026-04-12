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
import com.app.masterplan.presentation.ui.accounts.components.DirectorData
import com.app.masterplan.presentation.ui.accounts.components.EmployeeDataEditComponent
import com.app.masterplan.presentation.ui.accounts.components.UpdateAccountPasswordComponent
import com.app.masterplan.presentation.ui.accounts.viewmodel.EditAccountScreenViewModel
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState

@Composable
fun EditAccountScreen(
    navController: NavHostController,
    updateProfileId: String,
    viewModel: EditAccountScreenViewModel = hiltViewModel()
){

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val loadEditEmployeeFlow by viewModel.loadEditEmployeeFlow.collectAsState()


    val profileData by viewModel.profileData.collectAsState()

    val selectedDir by viewModel.selectedDir.collectAsState()
    val savingFlow = viewModel.savingFlow.collectAsState()


    val result: DirectorData? by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<DirectorData?>("selected_director", null)
        ?.collectAsState(initial = null)
        ?: remember { mutableStateOf(null) }




    LaunchedEffect(result) {
        viewModel.loadEditedProfile(updateProfileId)
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
                text = stringResource(id = R.string.edit_acc),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )


            when(loadEditEmployeeFlow){
                is MasterPlanState.Failure -> {
                    LaunchedEffect(loadEditEmployeeFlow) {
                        showToast = true
                        errorMessage = (loadEditEmployeeFlow as MasterPlanState.Failure).exception.toString()
                    }
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        UpdateAccountPasswordComponent(
                            profileData = profileData,
                            onPasswordChange = {viewModel.updatePassword(it)}
                        )

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
                                viewModel.updateProfile() },
                        ) {
                            Text(text = stringResource(id = R.string.save),)
                        }

                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
                MasterPlanState.Waiting -> null
            }



            CustomToastMessage(
                message = errorMessage,
                isVisible = showToast,
                onDismiss = { showToast = false },
            )
        }

    }


}