package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.presentation.ui.auth.viewModel.LoginScreenViewModel
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
        return LoginScreenViewModel(loginUseCase)
    }
}