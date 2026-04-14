package com.app.masterplan.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.masterplan.presentation.ui.accounts.screens.AccountsListScreen
import com.app.masterplan.presentation.ui.accounts.screens.CreateAccountScreen
import com.app.masterplan.presentation.ui.accounts.screens.EditAccountScreen
import com.app.masterplan.presentation.ui.accounts.screens.SelectDirectorScreen
import com.app.masterplan.presentation.ui.accounts.viewmodel.EditAccountScreenViewModel
import com.app.masterplan.presentation.ui.auth.screens.LoginScreen
import com.app.masterplan.presentation.ui.employees.screens.EmployeeListScreen
import com.app.masterplan.presentation.ui.options.screens.OptionsScreen
import com.app.masterplan.presentation.ui.plans.screen.CreateNewPlanScreen
import com.app.masterplan.presentation.ui.plans.screen.PlansListScreen
import com.app.masterplan.presentation.ui.plans.screen.UpdatePlanScreen
import com.app.masterplan.presentation.ui.plans.viewmodel.UpdatePlanViewModel
import com.app.masterplan.presentation.ui.profile.screens.ProfileScreen
import com.app.masterplan.presentation.ui.reports.screens.ReportListScreen
import com.app.masterplan.presentation.ui.requests.screens.NewAnswerScreen
import com.app.masterplan.presentation.ui.requests.screens.NewRequestScreen
import com.app.masterplan.presentation.ui.requests.screens.RequestsListScreen
import com.app.masterplan.presentation.ui.tasks.screens.AssignedTasksScreen
import com.app.masterplan.presentation.ui.tasks.screens.CreateNewTaskScreen
import com.app.masterplan.presentation.ui.tasks.screens.SelectExecutorScreen
import com.app.masterplan.presentation.ui.tasks.screens.TaskSearchScreen
import com.app.masterplan.presentation.ui.tasks.screens.TasksFromPlanScreen
import com.app.masterplan.presentation.ui.tasks.screens.UpdateTaskScreen
import com.app.masterplan.presentation.ui.tasks.viewModel.CreateNewTaskViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.TasksFromPlanScreenViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.UpdateTaskViewModel


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
        composable("new_profile"){ CreateAccountScreen(navController) }
        composable("director_selection"){ SelectDirectorScreen(navController)}
        composable("edit_profile/{employeeId}"){
            it.arguments?.getString("employeeId")?.let { employeeId ->
                val viewModel: EditAccountScreenViewModel = hiltViewModel()
                EditAccountScreen(navController,employeeId, viewModel)
            }
        }
        composable("options"){ OptionsScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("employees") { EmployeeListScreen() }
        composable("assigned_tasks") { AssignedTasksScreen(navController) }
        composable("search_assigned_tasks"){ TaskSearchScreen(navController) }
        composable("executor_selection"){ SelectExecutorScreen(navController)}
        composable("plans") { PlansListScreen(navController) }
        composable("edit_plan/{planId}"){
            it.arguments?.getString("planId")?.let { planId ->
                val viewModel: UpdatePlanViewModel = hiltViewModel()
                UpdatePlanScreen(planId,navController,viewModel)
            }
        }
        composable("create_plan") { CreateNewPlanScreen(navController) }
        composable("tasks_from_plan/{planId}"){
            it.arguments?.getString("planId")?.let { planId ->
                val viewModel: TasksFromPlanScreenViewModel = hiltViewModel()
                TasksFromPlanScreen(planId, navController, viewModel)
            }
        }
        composable("create_report_plan/{planId}"){
            it.arguments?.getString("planId")?.let { planId ->

            }
        }
        composable("create_report_task/{taskId}"){
            it.arguments?.getString("taskId")?.let { planId ->

            }
        }
        composable("selected_executor") { SelectExecutorScreen(navController) }
        composable("add_task_to_plan/{planId}"){
            it.arguments?.getString("planId")?.let { planId ->
                val viewModel: CreateNewTaskViewModel = hiltViewModel()
                CreateNewTaskScreen(planId,navController,viewModel)
            }
        }
        composable("edit_task/{taskId}"){
            it.arguments?.getString("taskId")?.let { taskId ->
                val viewModel: UpdateTaskViewModel = hiltViewModel()
                UpdateTaskScreen(taskId, navController, viewModel )
            }
        }
        composable("reports"){ ReportListScreen(navController) }

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