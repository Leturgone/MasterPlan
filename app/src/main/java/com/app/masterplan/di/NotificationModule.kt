package com.app.masterplan.di

import android.content.Context
import com.app.masterplan.domain.ports.NotificationPort
import com.app.masterplan.framework.adapter.NotificationAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationPort(@ApplicationContext context: Context): NotificationPort {
        return NotificationAdapter(context)
    }
}