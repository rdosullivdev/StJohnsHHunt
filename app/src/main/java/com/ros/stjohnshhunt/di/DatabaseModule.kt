package com.ros.stjohnshhunt.di

import android.content.Context
import com.ros.stjohnshhunt.db.AppDatabase
import com.ros.stjohnshhunt.db.HouseDao
import com.ros.stjohnshhunt.db.SearchConfigDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideHouseDao(appDatabase: AppDatabase): HouseDao {
        return appDatabase.houseDao()
    }

    @Provides
    fun provideSearchConfigDao(appDatabase: AppDatabase): SearchConfigDao {
        return appDatabase.searchConfigDao()
    }
}
