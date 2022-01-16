package com.ros.stjohnshhunt.di

import android.content.Context
import com.ros.stjohnshhunt.api.RealtyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRealtyService(@ApplicationContext appContext: Context): RealtyService {
        return RealtyService.create(appContext)
    }
}