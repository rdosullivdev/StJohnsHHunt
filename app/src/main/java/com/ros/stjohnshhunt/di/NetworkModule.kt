package com.ros.stjohnshhunt.di

import com.ros.stjohnshhunt.api.RealtyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRealtyService(): RealtyService {
        return RealtyService.create()
    }
}