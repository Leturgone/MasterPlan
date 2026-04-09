package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.domain.useacse.adminRequests.ChangeAdminRequestStatusUseCase
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminAnswerUseCase
import com.app.masterplan.domain.useacse.adminRequests.CreateAdminRequestUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminAnswerForRequestUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminRequestUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetAdminRequestsListUseCase
import com.app.masterplan.domain.useacse.adminRequests.GetCreatedAdminRequestsBySenderListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminRequestsUseCaseModule {

    @Provides
    @Singleton
    fun provideChangeAdminRequestStatusUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): ChangeAdminRequestStatusUseCase {
        return ChangeAdminRequestStatusUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideCreateAdminAnswerUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): CreateAdminAnswerUseCase {
        return CreateAdminAnswerUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideCreateAdminRequestUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): CreateAdminRequestUseCase {
        return CreateAdminRequestUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideGetAdminAnswerForRequestUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): GetAdminAnswerForRequestUseCase {
        return GetAdminAnswerForRequestUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideGetAdminRequestsListUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): GetAdminRequestsListUseCase {
        return GetAdminRequestsListUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideGetAdminRequestUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): GetAdminRequestUseCase {
        return  GetAdminRequestUseCase(adminRequestsRepository)
    }


    @Provides
    @Singleton
    fun provideGetCreatedAdminRequestsBySenderListUseCase(
        adminRequestsRepository: AdminRequestsRepository
    ): GetCreatedAdminRequestsBySenderListUseCase {
        return GetCreatedAdminRequestsBySenderListUseCase(adminRequestsRepository)
    }


}