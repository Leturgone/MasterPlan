package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.EmployeeRepository
import com.app.masterplan.domain.useacse.employee.CreateEmployeeUseCase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object EmployeeUseCaseModule {

    @Provides
    @Singleton
    fun provideCreateEmployeeUseCase(employeeRepository: EmployeeRepository): CreateEmployeeUseCase {
        return CreateEmployeeUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideExportDirEmployeesUseCase(employeeRepository: EmployeeRepository): ExportDirEmployeesUseCase {
        return ExportDirEmployeesUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideGetAllDirectorEmployeesUseCase(employeeRepository: EmployeeRepository): GetAllDirectorEmployeesUseCase {
        return GetAllDirectorEmployeesUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideGetAllEmployeesUseCase(employeeRepository: EmployeeRepository): GetAllEmployeesUseCase {
        return GetAllEmployeesUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideGetDirEmployeesWithoutTasksUseCase(employeeRepository: EmployeeRepository): GetDirEmployeesWithoutTasksUseCase {
        return GetDirEmployeesWithoutTasksUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideGetEmployeeByIdUseCase(employeeRepository: EmployeeRepository): GetEmployeeByIdUseCase {
        return GetEmployeeByIdUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideGetProfileInformationUseCas(employeeRepository: EmployeeRepository): GetProfileInformationUseCase {
        return GetProfileInformationUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideSearchDirEmployeeByNameUseCase(employeeRepository: EmployeeRepository): SearchDirEmployeeByNameUseCase{
        return SearchDirEmployeeByNameUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideSearchEmployeeByNameUseCase(employeeRepository: EmployeeRepository): SearchEmployeeByNameUseCase {
        return SearchEmployeeByNameUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideSortDirEmployeesByRatingUseCase(employeeRepository: EmployeeRepository): SortDirEmployeesByRatingUseCase {
        return SortDirEmployeesByRatingUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideSortDirEmployeesByWorkloadUseCase(employeeRepository: EmployeeRepository): SortDirEmployeesByWorkloadUseCase {
        return SortDirEmployeesByWorkloadUseCase(employeeRepository)
    }


    @Provides
    @Singleton
    fun provideUpdateEmployeeUseCase(employeeRepository: EmployeeRepository): UpdateEmployeeUseCase {
        return UpdateEmployeeUseCase(employeeRepository)
    }


}