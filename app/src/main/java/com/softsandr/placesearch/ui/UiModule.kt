package com.softsandr.placesearch.ui

import com.softsandr.placesearch.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Module
abstract class UiModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainActivity(): MainActivity
}