package com.softsandr.placesearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.api.SearchApiResponse
import com.softsandr.placesearch.api.Venue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
class SearchViewModel @Inject constructor(private val apiClient: ApiClient): ViewModel() {
    private var disposable: CompositeDisposable? = CompositeDisposable()
    private val loading = MutableLiveData<Boolean>()
    private val venues = MutableLiveData<List<Venue>>()
    private val venuesLoadError = MutableLiveData<Boolean>()

    fun getVenues(): LiveData<List<Venue>?> = venues
    fun getError(): LiveData<Boolean?> = venuesLoadError
    fun getLoading(): LiveData<Boolean?> = loading

    fun findVenueById(id: String) : Venue? = venues.value?.first { it.id == id }

    fun searchForPlaces(near: String, query: String, limit: Int = 20) {
        loading.value = true
        disposable?.add(apiClient.search(near, query, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<SearchApiResponse>() {
                override fun onSuccess(value: SearchApiResponse) {
                    loading.value = false
                    venues.value = value.response.venues
                    venuesLoadError.value = value.response.venues.isEmpty()
                }

                override fun onError(e: Throwable) {
                    loading.value = false
                    venuesLoadError.value = true
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.clear()
        disposable = null
    }
}