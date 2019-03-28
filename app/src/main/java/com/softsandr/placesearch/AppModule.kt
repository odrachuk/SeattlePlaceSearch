package com.softsandr.placesearch

import android.app.Application
import android.content.Context
import com.softsandr.placesearch.ui.UiModule
import com.softsandr.placesearch.ui.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Module(includes = [AndroidInjectionModule::class, UiModule::class, ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(app: Application): Context = app.applicationContext
}