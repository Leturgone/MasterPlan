package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.useacse.auth.LoginUseCase
import com.app.masterplan.presentation.ui.auth.viewModel.LoginViewModel
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
    ): LoginViewModel {
        val loginUseCase = LoginUseCase(authRepository)
        return LoginViewModel(loginUseCase)
    }
}