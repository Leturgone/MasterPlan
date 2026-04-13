package com.app.masterplan.di

import com.app.masterplan.data.api.adminRequestsApi.AdminRequestsApi
import com.app.masterplan.data.api.authApi.AuthApi
import com.app.masterplan.data.api.employeeApi.EmployeeApi
import com.app.masterplan.data.api.filesApi.FilesApi
import com.app.masterplan.data.api.plansApi.PlansTasksApi
import com.app.masterplan.data.api.reportsApi.ReportsApi
import com.app.masterplan.data.api.userManagementApi.UserManagementApi
import com.app.masterplan.data.repository.AdminRequestsRepositoryImpl
import com.app.masterplan.data.repository.AuthRepositoryImpl
import com.app.masterplan.data.repository.DocumentRepositoryImpl
import com.app.masterplan.data.repository.EmployeeRepositoryImpl
import com.app.masterplan.data.repository.PlanRepositoryImpl
import com.app.masterplan.data.repository.ReportRepositoryImpl
import com.app.masterplan.data.repository.SearchHistoryRepositoryImpl
import com.app.masterplan.data.repository.ThemeRepositoryImpl
import com.app.masterplan.data.repository.UserRepositoryImpl
import com.app.masterplan.data.storage.EmployeeIdStorage
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.data.storage.SearchHistoryDataStorage
import com.app.masterplan.data.storage.ThemeDataStorage
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.domain.repository.remote.AdminRequestsRepository
import com.app.masterplan.domain.repository.remote.AuthRepository
import com.app.masterplan.domain.repository.remote.DocumentRepository
import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.domain.repository.remote.PlanRepository
import com.app.masterplan.domain.repository.remote.ReportRepository
import com.app.masterplan.domain.repository.remote.SearchHistoryRepository
import com.app.masterplan.domain.repository.remote.ThemeRepository
import com.app.masterplan.domain.repository.remote.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideAdminRequestRepository(api: AdminRequestsApi, tokenStorage: TokenDataStorage): AdminRequestsRepository{
        return AdminRequestsRepositoryImpl(api,tokenStorage)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi,
                              tokenStorage: TokenDataStorage,
                              searchHistoryDataStorage: SearchHistoryDataStorage,
                              localEmployeeIdStorage: EmployeeIdStorage
                              ): AuthRepository{
        return AuthRepositoryImpl(api,tokenStorage,
            searchHistoryDataStorage,localEmployeeIdStorage)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(
        filesApi: FilesApi, localFileDataSource: LocalFileDataStorage, tokenStorage: TokenDataStorage
    ): DocumentRepository{
        return DocumentRepositoryImpl(filesApi, localFileDataSource, tokenStorage)
    }


    @Provides
    @Singleton
    fun provideEmployeeRepository(
        employeeApi: EmployeeApi,
        tokenStorage: TokenDataStorage,
        localFileDataSource: LocalFileDataStorage,
        localEmployeeIdStorage: EmployeeIdStorage
    ): EmployeeRepository {
        return EmployeeRepositoryImpl(employeeApi, tokenStorage, localFileDataSource, localEmployeeIdStorage)
    }


    @Provides
    @Singleton
    fun providePlanRepository(
        planApi: PlansTasksApi, tokenStorage: TokenDataStorage, localFileDataSource: LocalFileDataStorage
    ): PlanRepository {
        return PlanRepositoryImpl(planApi, tokenStorage, localFileDataSource)
    }


    @Provides
    @Singleton
    fun provideReportRepository(
        reportsApi: ReportsApi, tokenStorage: TokenDataStorage
    ): ReportRepository {
        return ReportRepositoryImpl(reportsApi, tokenStorage)
    }


    @Provides
    @Singleton
    fun provideUserRepository(
        userManagementApi: UserManagementApi, tokenStorage: TokenDataStorage
    ): UserRepository {
        return UserRepositoryImpl(userManagementApi, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(
        searchHistoryDataStorage: SearchHistoryDataStorage
    ): SearchHistoryRepository{
        return SearchHistoryRepositoryImpl(searchHistoryDataStorage)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        themeDataStorage: ThemeDataStorage
    ): ThemeRepository{
        return ThemeRepositoryImpl(themeDataStorage)
    }

}