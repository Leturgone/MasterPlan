package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.repository.remote.DocumentRepository
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.domain.repository.remote.PlanRepository
import com.app.masterplan.domain.repository.remote.ReportRepository
import com.app.masterplan.domain.repository.remote.SearchHistoryRepository
import com.app.masterplan.domain.repository.remote.TaskRepository
import com.app.masterplan.domain.repository.remote.ThemeRepository
import com.app.masterplan.domain.repository.remote.UserRepository
import com.app.masterplan.domain.useacse.adminRequests.ChangeAdminRequestStatusUseCase
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminAnswerUseCase
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminRequestUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminAnswerForRequestUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminRequestsListUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetCreatedAdminRequestsBySenderListUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.domain.useacse.auth.LogoutUseCase
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import com.app.masterplan.domain.useacse.employee.ExportDirEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.GetAllDirectorEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.GetAllEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.GetDirEmployeesWithoutTasksUseCase
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.employee.GetProfileInformationUseCase
import com.app.masterplan.domain.useacse.employee.SearchDirEmployeeByNameUseCase
import com.app.masterplan.domain.useacse.employee.SearchEmployeeByNameUseCase
import com.app.masterplan.domain.useacse.employee.SortDirEmployeesByRatingUseCase
import com.app.masterplan.domain.useacse.employee.SortDirEmployeesByWorkloadUseCase
import com.app.masterplan.domain.useacse.employee.UpdateEmployeeUseCase
import com.app.masterplan.domain.useacse.plans.AddTaskToPlanUseCase
import com.app.masterplan.domain.useacse.plans.ChangePlanStatusUseCase
import com.app.masterplan.domain.useacse.plans.ChangeTaskStatusUseCase
import com.app.masterplan.domain.useacse.plans.CreatePlanUseCase
import com.app.masterplan.domain.useacse.plans.DeletePlanUseCase
import com.app.masterplan.domain.useacse.plans.DeleteTaskFromPlanUseCase
import com.app.masterplan.domain.useacse.plans.ExportPlanUseCase
import com.app.masterplan.domain.useacse.plans.FilterAssignedTasksByStatusUseCase
import com.app.masterplan.domain.useacse.plans.FilterDirPlansByStatusUseCase
import com.app.masterplan.domain.useacse.plans.FilterPlanTasksByStatusUseCase
import com.app.masterplan.domain.useacse.plans.GetAssignedTasksUseCase
import com.app.masterplan.domain.useacse.plans.GetDirPlansUseCase
import com.app.masterplan.domain.useacse.plans.GetPlanInfUseCase
import com.app.masterplan.domain.useacse.plans.GetTaskInfUseCase
import com.app.masterplan.domain.useacse.plans.GetTasksFromPlanUseCase
import com.app.masterplan.domain.useacse.plans.SearchAssignedTasksByTitleUseCase
import com.app.masterplan.domain.useacse.plans.SortAssignedTasksByEndDateUseCase
import com.app.masterplan.domain.useacse.plans.SortDirPlansByEndDateUseCase
import com.app.masterplan.domain.useacse.plans.SortPlanTasksByEndDateUseCase
import com.app.masterplan.domain.useacse.plans.UpdatePlanUseCase
import com.app.masterplan.domain.useacse.plans.UpdateTaskUseCase
import com.app.masterplan.domain.useacse.reports.ChangeReportStatusUseCase
import com.app.masterplan.domain.useacse.reports.DeleteReportUseCase
import com.app.masterplan.domain.useacse.reports.FilterByStatusCreatedReportsUseCase
import com.app.masterplan.domain.useacse.reports.FilterByStatusSubordinatesTaskReportsUseCase
import com.app.masterplan.domain.useacse.reports.GetCreatedReportsUseCase
import com.app.masterplan.domain.useacse.reports.GetSubordinatesTaskReportsUseCase
import com.app.masterplan.domain.useacse.searchHistory.ClearSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.GetSearchHistoryUseCase
import com.app.masterplan.domain.useacse.searchHistory.SaveSearchHistoryUseCase
import com.app.masterplan.domain.useacse.theme.ChangeThemeUseCase
import com.app.masterplan.domain.useacse.theme.GetCurrentThemeIsDarkUseCase
import com.app.masterplan.domain.useacse.userManagement.CreateUserUseCase
import com.app.masterplan.domain.useacse.userManagement.DeleteUserUseCase
import com.app.masterplan.domain.useacse.userManagement.GetUserByIdUseCase
import com.app.masterplan.domain.useacse.userManagement.ResetPasswordUseCase
import com.app.masterplan.presentation.ui.accounts.viewmodel.AccountCardViewModel
import com.app.masterplan.presentation.ui.accounts.viewmodel.AccountListViewModel
import com.app.masterplan.presentation.ui.accounts.viewmodel.CreateAccountScreenViewModel
import com.app.masterplan.presentation.ui.accounts.viewmodel.EditAccountScreenViewModel
import com.app.masterplan.presentation.ui.auth.viewModel.LoginScreenViewModel
import com.app.masterplan.presentation.ui.bottomBar.viewModel.BottomBarViewModel
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeCardViewModel
import com.app.masterplan.presentation.ui.employees.viewmodel.EmployeeListScreenViewModel
import com.app.masterplan.presentation.ui.options.viewmodel.OptionsViewModel
import com.app.masterplan.presentation.ui.plans.viewmodel.CreateNewPlanViewModel
import com.app.masterplan.presentation.ui.plans.viewmodel.PlansListScreenViewModel
import com.app.masterplan.presentation.ui.plans.viewmodel.UpdatePlanViewModel
import com.app.masterplan.presentation.ui.profile.viewmodel.ProfileScreenViewModel
import com.app.masterplan.presentation.ui.reports.viewmodel.ReportsScreenViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.NewAnswerScreenViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.NewRequestsScreenViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestCardViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestsListScreenViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.AssignedTasksScreenViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.CreateNewTaskViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.TaskSearchScreenViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.TasksFromPlanScreenViewModel
import com.app.masterplan.presentation.ui.tasks.viewModel.UpdateTaskViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideLoginViewModel(
        authRepository: AuthRepository,
        employeeRepository: EmployeeRepository
    ): LoginScreenViewModel {
        val loginUseCase = LoginUseCase(authRepository)
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        return LoginScreenViewModel(loginUseCase, getLocalEmpIdUseCase, getUserRoleUseCase)
    }


    @Provides
    @Singleton
    fun provideBottomBarViewModel(
        authRepository: AuthRepository
    ): BottomBarViewModel {
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        return BottomBarViewModel(getUserRoleUseCase)
    }

    @Provides
    @Singleton
    fun provideRequestsListScreenViewModel(
        authRepository: AuthRepository,
        adminRequestsRepository: AdminRequestsRepository,
        employeeRepository: EmployeeRepository
    ): RequestsListScreenViewModel {
        val getAdminRequestsListUseCase = GetAdminRequestsListUseCase(adminRequestsRepository)
        val getCreatedAdminRequestsListUseCase = GetCreatedAdminRequestsBySenderListUseCase(adminRequestsRepository)
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)

        return RequestsListScreenViewModel(
            getAdminRequestsListUseCase,
            getCreatedAdminRequestsListUseCase,
            getLocalEmpIdUseCase,
            getUserRoleUseCase
        )
    }


    @Provides
    @Singleton
    fun provideRequestCardViewModel(
        employeeRepository: EmployeeRepository,
        adminRequestsRepository: AdminRequestsRepository,
        authRepository: AuthRepository
    ): RequestCardViewModel {
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        val updateAdminRequestStatusUseCase = ChangeAdminRequestStatusUseCase(adminRequestsRepository)
        val getAdminAnswerForRequestUseCase = GetAdminAnswerForRequestUseCase(adminRequestsRepository)
        val getEmployeeByIdUseCase = GetEmployeeByIdUseCase(employeeRepository)
        return RequestCardViewModel(
            getUserRoleUseCase, updateAdminRequestStatusUseCase,
            getAdminAnswerForRequestUseCase,  getEmployeeByIdUseCase
        )
    }

    @Provides
    @Singleton
    fun provideNewAnswerScreenViewModel(
        adminRequestsRepository: AdminRequestsRepository,
    ): NewAnswerScreenViewModel {
        val createAdminAnswerUseCase = CreateAdminAnswerUseCase(adminRequestsRepository)
        return NewAnswerScreenViewModel(
            createAdminAnswerUseCase
        )
    }

    @Provides
    @Singleton
    fun provideNewRequestsScreenViewModel(
        employeeRepository: EmployeeRepository,
        adminRequestsRepository: AdminRequestsRepository,
    ): NewRequestsScreenViewModel {
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val createAdminRequestUseCase = CreateAdminRequestUseCase(adminRequestsRepository)
        return NewRequestsScreenViewModel(
            getLocalEmpIdUseCase, createAdminRequestUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAccountCardViewModel(
        employeeRepository: EmployeeRepository,
        userRepository: UserRepository
    ): AccountCardViewModel {
        val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
        val getEmployeeByIdUseCase = GetEmployeeByIdUseCase(employeeRepository)
        val deleteUserUseCase = DeleteUserUseCase(userRepository)
        return AccountCardViewModel(
            getUserByIdUseCase,
            getEmployeeByIdUseCase,
            deleteUserUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAccountListViewModel(
        employeeRepository: EmployeeRepository,
        searchHistoryRepository: SearchHistoryRepository,
    ): AccountListViewModel {
        val getAllEmployeesUseCase = GetAllEmployeesUseCase(employeeRepository)
        val getSearchHistoryUseCase = GetSearchHistoryUseCase(searchHistoryRepository)
        val saveSearchHistoryUseCase = SaveSearchHistoryUseCase(searchHistoryRepository)
        val clearSearchHistoryUseCase = ClearSearchHistoryUseCase(searchHistoryRepository)
        val searchEmployeeByNameUseCase = SearchEmployeeByNameUseCase(employeeRepository)
        return AccountListViewModel(
            getAllEmployeesUseCase,
            getSearchHistoryUseCase,
            saveSearchHistoryUseCase,
            clearSearchHistoryUseCase,
            searchEmployeeByNameUseCase
        )
    }

    @Provides
    @Singleton
    fun provideCreateAccountScreenViewModel(
        userRepository: UserRepository
    ): CreateAccountScreenViewModel{
        val createUserUseCase = CreateUserUseCase(userRepository)
        return CreateAccountScreenViewModel(
            createUserUseCase
        )
    }


    @Provides
    @Singleton
    fun provideEditAccountScreenViewModel(
        userRepository: UserRepository,
        employeeRepository: EmployeeRepository,
    ): EditAccountScreenViewModel{
        val getEmployeeByIdUseCase = GetEmployeeByIdUseCase(employeeRepository)
        val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
        val resetPasswordUseCase = ResetPasswordUseCase(userRepository)
        val updateEmployeeUseCase = UpdateEmployeeUseCase(employeeRepository)
        return EditAccountScreenViewModel(
            getEmployeeByIdUseCase,
            getUserByIdUseCase,
            resetPasswordUseCase,
            updateEmployeeUseCase
        )
    }

    @Provides
    @Singleton
    fun provideOptionsViewModel(
        themeRepository: ThemeRepository,
        authRepository: AuthRepository
    ): OptionsViewModel {
        val changeThemeUseCase = ChangeThemeUseCase(themeRepository)
        val getCurrentThemeIsDarkUseCase = GetCurrentThemeIsDarkUseCase(themeRepository)
        val logoutUseCase = LogoutUseCase(authRepository)
        return OptionsViewModel(
            changeThemeUseCase,
            getCurrentThemeIsDarkUseCase,
            logoutUseCase
        )
    }


    @Provides
    @Singleton
    fun provideProfileViewModel(
        employeeRepository: EmployeeRepository,
        authRepository: AuthRepository
    ): ProfileScreenViewModel {
        val getProfileInformationUseCase = GetProfileInformationUseCase(employeeRepository)
        val getEmployeeIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        return ProfileScreenViewModel(
            getProfileInformationUseCase,
            getEmployeeIdUseCase,
            getUserRoleUseCase
        )
    }


    @Provides
    @Singleton
    fun provideEmployeeCardViewModel(
        employeeRepository: EmployeeRepository,
        taskRepository: TaskRepository
    ): EmployeeCardViewModel {
        val getProfileInformationUseCase = GetProfileInformationUseCase(employeeRepository)
        val getAssignedTasksUseCase = GetAssignedTasksUseCase(taskRepository)
        return EmployeeCardViewModel(
            getProfileInformationUseCase,
            getAssignedTasksUseCase
        )
    }


    @Provides
    @Singleton
    fun provideEmployeeListScreenViewModel(
        employeeRepository: EmployeeRepository,
        searchHistoryRepository: SearchHistoryRepository
    ): EmployeeListScreenViewModel {
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getAllDirectorEmployeesUseCase = GetAllDirectorEmployeesUseCase(employeeRepository)
        val sortDirEmployeesByRatingUseCase = SortDirEmployeesByRatingUseCase(employeeRepository)
        val sortDirEmployeesByWorkloadUseCase = SortDirEmployeesByWorkloadUseCase(employeeRepository)
        val getDirEmployeesWithoutTasksUseCase = GetDirEmployeesWithoutTasksUseCase(employeeRepository)
        val searchDirEmployeeByNameUseCase = SearchDirEmployeeByNameUseCase(employeeRepository)
        val getSearchHistoryUseCase = GetSearchHistoryUseCase(searchHistoryRepository)
        val saveSearchHistoryUseCase = SaveSearchHistoryUseCase(searchHistoryRepository)
        val clearSearchHistoryUseCase = ClearSearchHistoryUseCase(searchHistoryRepository)
        val exportDirEmployeesUseCase = ExportDirEmployeesUseCase(employeeRepository)
        return EmployeeListScreenViewModel(
            getLocalEmpIdUseCase,
            getAllDirectorEmployeesUseCase,
            sortDirEmployeesByRatingUseCase,
            sortDirEmployeesByWorkloadUseCase,
            getDirEmployeesWithoutTasksUseCase,
            searchDirEmployeeByNameUseCase,
            getSearchHistoryUseCase,
            saveSearchHistoryUseCase,
            clearSearchHistoryUseCase,
            exportDirEmployeesUseCase
        )
    }



    @Provides
    @Singleton
    fun provideAssignedTasksScreenViewModel(
        employeeRepository: EmployeeRepository,
        taskRepository: TaskRepository,
        documentRepository: DocumentRepository
    ): AssignedTasksScreenViewModel {

        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getAssignedTasksUseCase = GetAssignedTasksUseCase(taskRepository)
        val filterAssignedTasksByStatusUseCase = FilterAssignedTasksByStatusUseCase(taskRepository)
        val sortAssignedTasksByEndDateUseCase = SortAssignedTasksByEndDateUseCase(taskRepository)
        val downloadFileUseCase = DownloadFileUseCase(documentRepository)
        val changeTaskStatusUseCase = ChangeTaskStatusUseCase(taskRepository)

        return AssignedTasksScreenViewModel(
            getLocalEmpIdUseCase,
            getAssignedTasksUseCase,
            filterAssignedTasksByStatusUseCase,
            sortAssignedTasksByEndDateUseCase,
            downloadFileUseCase,
            changeTaskStatusUseCase
        )
    }

    @Provides
    @Singleton
    fun provideTaskSearchScreenViewModel(
        employeeRepository: EmployeeRepository,
        taskRepository: TaskRepository,
        searchHistoryRepository: SearchHistoryRepository,
        documentRepository: DocumentRepository
    ): TaskSearchScreenViewModel {
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getAssignedTasksUseCase = GetAssignedTasksUseCase(taskRepository)
        val getSearchHistoryUseCase = GetSearchHistoryUseCase(searchHistoryRepository)
        val saveSearchHistoryUseCase = SaveSearchHistoryUseCase(searchHistoryRepository)
        val clearSearchHistoryUseCase = ClearSearchHistoryUseCase(searchHistoryRepository)
        val searchAssignedTasksByTitleUseCase = SearchAssignedTasksByTitleUseCase(taskRepository)
        val downloadFileUseCase = DownloadFileUseCase(documentRepository)
        val changeTaskStatusUseCase = ChangeTaskStatusUseCase(taskRepository)

        return TaskSearchScreenViewModel(
            getLocalEmpIdUseCase,
            getAssignedTasksUseCase,
            getSearchHistoryUseCase,
            saveSearchHistoryUseCase,
            clearSearchHistoryUseCase,
            searchAssignedTasksByTitleUseCase,
            downloadFileUseCase,
            changeTaskStatusUseCase
        )
    }


    @Provides
    @Singleton
    fun providePlansListScreenViewModel(
        authRepository: AuthRepository,
        employeeRepository: EmployeeRepository,
        planRepository: PlanRepository,
        documentRepository: DocumentRepository
    ): PlansListScreenViewModel {
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getDirPlansUseCase = GetDirPlansUseCase(planRepository)
        val sortDirPlansByEndDateUseCase = SortDirPlansByEndDateUseCase(planRepository)
        val filterDirPlansByStatusUseCase = FilterDirPlansByStatusUseCase(planRepository)
        val downloadFileUseCase = DownloadFileUseCase(documentRepository)
        val deletePlanUseCase = DeletePlanUseCase(planRepository)
        val changePlanStatusUseCase = ChangePlanStatusUseCase(planRepository)

        return PlansListScreenViewModel(
            getUserRoleUseCase,
            getLocalEmpIdUseCase,
            getDirPlansUseCase,
            sortDirPlansByEndDateUseCase,
            filterDirPlansByStatusUseCase,
            downloadFileUseCase,
            deletePlanUseCase,
            changePlanStatusUseCase
        )
    }


    @Provides
    @Singleton
    fun provideUpdatePlanViewModel(
        planRepository: PlanRepository,
        documentRepository: DocumentRepository
    ):  UpdatePlanViewModel {
        val getPlanInfUseCase = GetPlanInfUseCase(planRepository)
        val updatePlanUseCase = UpdatePlanUseCase(planRepository)
        val attachFileUseCase = AttachFileUseCase(documentRepository)
        return  UpdatePlanViewModel(
            getPlanInfUseCase,
            updatePlanUseCase,
            attachFileUseCase
        )
    }


    @Provides
    @Singleton
    fun provideCreateNewPlanViewModel(
        employeeRepository: EmployeeRepository,
        planRepository: PlanRepository,
        documentRepository: DocumentRepository
    ): CreateNewPlanViewModel {
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val createNewPlanUseCase = CreatePlanUseCase(planRepository)
        val attachFileUseCase = AttachFileUseCase(documentRepository)
        return  CreateNewPlanViewModel(
            getLocalEmpIdUseCase,
            createNewPlanUseCase,
            attachFileUseCase
        )
    }


    @Provides
    @Singleton
    fun provideTasksFromPlanScreenViewModel(
        authRepository: AuthRepository,
        taskRepository: TaskRepository,
        documentRepository: DocumentRepository,
        planRepository: PlanRepository,
        employeeRepository: EmployeeRepository
    ): TasksFromPlanScreenViewModel {
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        val getTasksFromPlanUseCase = GetTasksFromPlanUseCase(taskRepository)
        val sortPlanTasksByEndDateUseCase = SortPlanTasksByEndDateUseCase(taskRepository)
        val filterPlanTasksByStatusUseCase = FilterPlanTasksByStatusUseCase(taskRepository)
        val downloadFileUseCase = DownloadFileUseCase(documentRepository)
        val exportPlanUseCase = ExportPlanUseCase(planRepository)
        val deleteTaskFromPlanUseCase = DeleteTaskFromPlanUseCase(taskRepository)
        val getEmployeeByIdUseCase = GetEmployeeByIdUseCase(employeeRepository)
        return TasksFromPlanScreenViewModel(
            getUserRoleUseCase,
            getTasksFromPlanUseCase,
            sortPlanTasksByEndDateUseCase,
            filterPlanTasksByStatusUseCase,
            downloadFileUseCase,
            exportPlanUseCase,
            deleteTaskFromPlanUseCase,
            getEmployeeByIdUseCase
        )
    }


    @Provides
    @Singleton
    fun provideCreateNewTaskViewModel(
        taskRepository: TaskRepository,
        documentRepository: DocumentRepository
    ): CreateNewTaskViewModel {
        val addTaskToPlanUseCase = AddTaskToPlanUseCase(taskRepository)
        val attachFileUseCase = AttachFileUseCase(documentRepository)
        return CreateNewTaskViewModel(
            addTaskToPlanUseCase,
            attachFileUseCase
        )
    }


    @Provides
    @Singleton
    fun provideUpdateTaskViewModel(
        taskRepository: TaskRepository,
        documentRepository: DocumentRepository
    ): UpdateTaskViewModel {
        val getTaskByIdUseCase = GetTaskInfUseCase(taskRepository)
        val updateTaskUseCase = UpdateTaskUseCase(taskRepository)
        val attachFileUseCase = AttachFileUseCase(documentRepository)
        return UpdateTaskViewModel(
            getTaskByIdUseCase,
            updateTaskUseCase,
            attachFileUseCase
        )
    }


    @Provides
    @Singleton
    fun provideReportsScreenViewModel(
        employeeRepository: EmployeeRepository,
        reportRepository: ReportRepository,
        documentRepository: DocumentRepository,
        taskRepository: TaskRepository,
        planRepository: PlanRepository,
        ): ReportsScreenViewModel {
        val getLocalEmpIdUseCase = GetLocalEmpIdUseCase(employeeRepository)
        val getEmployeeByIdUseCase = GetEmployeeByIdUseCase(employeeRepository)
        val changeReportStatusUseCase = ChangeReportStatusUseCase(reportRepository)
        val deleteReportUseCase = DeleteReportUseCase(reportRepository)
        val filterByStatusCreatedReportsUseCase = FilterByStatusCreatedReportsUseCase(reportRepository)
        val filterByStatusSubordinatesTaskReportsUseCase = FilterByStatusSubordinatesTaskReportsUseCase(reportRepository)
        val getCreatedReportsUseCase = GetCreatedReportsUseCase(reportRepository)
        val getSubordinatesTaskReportsUseCase = GetSubordinatesTaskReportsUseCase(reportRepository)
        val downloadFileUseCase = DownloadFileUseCase(documentRepository)
        val getTaskInfUseCase = GetTaskInfUseCase(taskRepository)
        val getPlanInfUseCase = GetPlanInfUseCase(planRepository)
        return ReportsScreenViewModel(
            getLocalEmpIdUseCase,
            getEmployeeByIdUseCase,
            changeReportStatusUseCase,
            deleteReportUseCase,
            filterByStatusCreatedReportsUseCase,
            filterByStatusSubordinatesTaskReportsUseCase,
            getCreatedReportsUseCase,
            getSubordinatesTaskReportsUseCase,
            downloadFileUseCase,
            getTaskInfUseCase,
            getPlanInfUseCase
        )
    }

}