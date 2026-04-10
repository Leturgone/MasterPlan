package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.useacse.auth.GetUserIdUseCase
import com.app.masterplan.domain.useacse.auth.GetUserRoleUseCase
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.presentation.ui.auth.viewModel.LoginScreenViewModel
import com.app.masterplan.presentation.ui.bottomBar.viewModel.BottomBarViewModel
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
        authRepository: AuthRepository
    ): LoginScreenViewModel {
        val loginUseCase = LoginUseCase(authRepository)
        val getUserIdUseCase = GetUserIdUseCase(authRepository)
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        return LoginScreenViewModel(loginUseCase, getUserIdUseCase, getUserRoleUseCase)
    }


    @Provides
    @Singleton
    fun provideBottomBarViewModel(
        authRepository: AuthRepository
    ): BottomBarViewModel {
        val getUserRoleUseCase = GetUserRoleUseCase(authRepository)
        return BottomBarViewModel(getUserRoleUseCase)
    }
}