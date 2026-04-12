package com.app.masterplan.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.masterplan.presentation.ui.accounts.screens.AccountsListScreen
import com.app.masterplan.presentation.ui.auth.screens.LoginScreen
import com.app.masterplan.presentation.ui.requests.screens.NewAnswerScreen
import com.app.masterplan.presentation.ui.requests.screens.NewRequestScreen
import com.app.masterplan.presentation.ui.requests.screens.RequestsListScreen
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestCardViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestsListScreenViewModel
import java.util.UUID


//themeViewModel: AppThemeViewModel,
//                  profileViewModel: ProfileViewModel
@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(innerPadding)
    ) {

        composable("login"){ LoginScreen(navController) }
        composable("requests") {
            RequestsListScreen(navController) }
        composable("new_request"){ NewRequestScreen(navController) }
        composable("new_answer/{requestId}"){
            it.arguments?.getString("requestId")?.let { requestId ->
                NewAnswerScreen(navController,requestId)
            }
        }
        composable("accounts"){ AccountsListScreen(navController) }
//        composable("tasks"){
//            TaskScreen(navController, profileViewModel = profileViewModel)
//        }
//        composable("new_task") { NewTaskScreen(navController) }
//        composable("new_task/{employeeName}/{employeeId}/{directorId}") {
//            val empName = it.arguments?.getString("employeeName")
//            val empId = it.arguments?.getString("employeeId")?.toInt()
//            val dirId = it.arguments?.getString("directorId")?.toInt()
//            NewTaskScreen(navController,employeeName = empName,employeeId =empId, directorId = dirId) }
//        composable("set_employee") { SetEmployeeScreen(navController)  }
//        composable("opt") { OptScreen(navController, themeViewModel = themeViewModel, profileViewModel = profileViewModel) }
//        composable("emp_list"){ EmployeesScreen() }
//        composable("resp"){ ReportScreen(profileViewModel) }
//        composable("profile"){ ProfileScreen(navController,profileViewModel) }
    }
}