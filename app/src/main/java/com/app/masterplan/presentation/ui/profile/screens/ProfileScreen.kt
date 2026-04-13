package com.app.masterplan.presentation.ui.profile.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.masterplan.R
import com.app.masterplan.presentation.ui.common.CardButton
import com.app.masterplan.presentation.ui.common.CustomToastMessage
import com.app.masterplan.presentation.ui.common.MasterPlanState
import com.app.masterplan.presentation.ui.profile.components.ProfileView
import com.app.masterplan.presentation.ui.profile.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
){

    var showToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val profile = viewModel.profileInformation.collectAsState()

    val showRequestsButton by viewModel.showRequestButton.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                Icon(imageVector = Icons.Default.Settings, contentDescription = "OptButton",
                    Modifier
                        .padding(end = 32.dp, top = 16.dp)
                        .clickable {
                            navController.navigate("options")
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            when(profile.value){
                is MasterPlanState.Failure -> LaunchedEffect(profile.value) {
                    showToast = true
                    errorMessage = (profile.value as MasterPlanState.Failure).exception.toString()
                }
                MasterPlanState.Loading -> CircularProgressIndicator()
                is MasterPlanState.Success -> {
                    val empProfile = (profile.value as MasterPlanState.Success).result
                    ProfileView(empProfile)
                }
                MasterPlanState.Waiting -> null
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showRequestsButton){
                CardButton(stringResource(R.string.requests_for_admin)) {
                    navController.navigate("requests")
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