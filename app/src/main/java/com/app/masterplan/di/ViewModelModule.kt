package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.domain.useacse.adminRequests.GetAdminRequestsListUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetCreatedAdminRequestsBySenderListUseCase
import com.app.masterplan.domain.useacse.employee.GetLocalEmpIdUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.presentation.ui.auth.viewModel.LoginScreenViewModel
import com.app.masterplan.presentation.ui.bottomBar.viewModel.BottomBarViewModel
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

}