package com.softsandr.placesearch.ui

import com.softsandr.placesearch.ui.details.DetailsActivity
import com.softsandr.placesearch.ui.search.SearchActivity
import com.softsandr.placesearch.ui.viewmodel.ViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Module(includes = [ViewModelModule::class])
abstract class UiModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainActivity(): SearchActivity

    @ContributesAndroidInjector
    internal abstract fun provideDetailsActivity(): DetailsActivity
}