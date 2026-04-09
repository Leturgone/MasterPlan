package com.app.masterplan.di

import com.app.masterplan.domain.repository.remote.DocumentRepository
import com.app.masterplan.domain.useacse.document.AttachFileUseCase
import com.app.masterplan.domain.useacse.document.DownloadFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DocumentUseCaseModule {

    @Provides
    @Singleton
    fun provideAttachFileUseCase(documentRepository: DocumentRepository): AttachFileUseCase {
        return AttachFileUseCase(documentRepository)
    }


    @Provides
    @Singleton
    fun provideDownloadFileUseCase(documentRepository: DocumentRepository): DownloadFileUseCase{
        return DownloadFileUseCase(documentRepository)
    }
}