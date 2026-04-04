package com.app.masterplan.di

import com.app.masterplan.data.api.adminRequestsApi.AdminRequestsApi
import com.app.masterplan.data.api.authApi.AuthApi
import com.app.masterplan.data.api.employeeApi.EmployeeApi
import com.app.masterplan.data.api.filesApi.FilesApi
import com.app.masterplan.data.api.plansApi.PlansApi
import com.app.masterplan.data.api.reportsApi.ReportsApi
import com.app.masterplan.data.api.userManagementApi.UserManagementApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Увеличение времени ожидания соединения
            .readTimeout(60, TimeUnit.SECONDS)    // Увеличение времени ожидания чтения данных
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        return client
    }


    @Provides
    @Singleton
    fun provideAdminRequestsApi(client: OkHttpClient): AdminRequestsApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/requests")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AdminRequestsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/auth")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }


    @Provides
    @Singleton
    fun provideEmployeeApi(client: OkHttpClient): EmployeeApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/employees")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(EmployeeApi::class.java)
    }


    @Provides
    @Singleton
    fun provideFilesApi(client: OkHttpClient): FilesApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/files")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FilesApi::class.java)
    }


    @Provides
    @Singleton
    fun providePlansApi(client: OkHttpClient): PlansApi {
        return Retrofit
            .Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PlansApi::class.java)
    }


    @Provides
    @Singleton
    fun provideReportsApi(client: OkHttpClient): ReportsApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/reports")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ReportsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideUserManagementApi(client: OkHttpClient): UserManagementApi {
        return Retrofit
            .Builder().baseUrl("$API_URL/users/admin")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UserManagementApi::class.java)
    }


}