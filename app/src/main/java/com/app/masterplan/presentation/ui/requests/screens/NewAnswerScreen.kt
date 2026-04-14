package com.app.masterplan.presentation.ui.requests.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.requests.components.BigTextFieldWithButton
import com.app.masterplan.presentation.ui.requests.viewmodel.NewAnswerScreenViewModel

@Composable
fun NewAnswerScreen(
    navController: NavHostController,
    requestId: String,
    viewModel: NewAnswerScreenViewModel = hiltViewModel()
) {

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val creatingStatus = viewModel.creatingStatus.collectAsState()

    Box(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.new_answer),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            when(creatingStatus.value){
                is MasterPlanState.Failure -> {
                    showToast = true
                    errorMessage = (creatingStatus.value as MasterPlanState.Failure).exception.message ?: ""
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> navController.popBackStack()
                MasterPlanState.Waiting -> null
            }

            BigTextFieldWithButton(
                fieldTitle = stringResource(id = R.string.new_answer),
                buttonTitle = stringResource(id = R.string.create_answer),
            ) {
                viewModel.createNewAnswer(requestId,it)
            }


        }

        CustomToastMessage(
            message = errorMessage,
            isVisible = showToast,
            onDismiss = { showToast = false },
        )
    }
}