package com.softsandr.placesearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softsandr.placesearch.di.ViewModelKey
import com.softsandr.placesearch.ui.details.DetailsViewModel
import com.softsandr.placesearch.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}