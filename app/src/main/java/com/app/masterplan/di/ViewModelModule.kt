package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.domain.repository.remote.SearchHistoryRepository
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
import com.app.masterplan.domain.useacse.employee.GetAllEmployeesUseCase
import com.app.masterplan.domain.useacse.employee.GetEmployeeByIdUseCase
import com.app.masterplan.domain.useacse.employee.SearchEmployeeByNameUseCase
import com.app.masterplan.domain.useacse.employee.UpdateEmployeeUseCase
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
import com.app.masterplan.presentation.ui.options.viewmodel.OptionsViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.NewAnswerScreenViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.NewRequestsScreenViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestCardViewModel
import com.app.masterplan.presentation.ui.requests.viewmodel.RequestsListScreenViewModel
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


}