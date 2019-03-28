package com.softsandr.placesearch.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
@Module
class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "room.db")
        .build()

    @Provides
    @Singleton
    fun provideSavedVenuesDao(appDatabase: AppDatabase) = appDatabase.savedVenuesDao()
}