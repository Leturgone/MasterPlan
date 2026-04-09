package com.app.masterplan.di

import android.content.Context
import com.app.masterplan.data.storage.LocalFileDataStorage
import com.app.masterplan.data.storage.TokenDataStorage
import com.app.masterplan.framework.storage.datastore.TokenDataStorageImpl
import com.app.masterplan.framework.storage.filesStorage.LocalFileDataStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideTokenDataStorage(@ApplicationContext context: Context): TokenDataStorage{
        return TokenDataStorageImpl(context)
    }


    @Provides
    @Singleton
    fun provideLocalFileDataStorage(@ApplicationContext context: Context): LocalFileDataStorage {
        return LocalFileDataStorageImpl(context)
    }

}