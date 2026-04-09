package com.app.masterplan.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.masterplan.presentation.ui.auth.screens.LoginScreen


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