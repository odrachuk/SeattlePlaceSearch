package com.softsandr.placesearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.api.SearchApiResponse
import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.db.dao.SavedVenuesDao
import com.softsandr.placesearch.db.entity.SavedVenue
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
class SearchViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val savedVenuesDao: SavedVenuesDao
) : ViewModel() {
    var disposable: CompositeDisposable? = CompositeDisposable()
    private val loading = MutableLiveData<Boolean>()
    private val venues = MutableLiveData<List<Venue>>()
    private val venuesLoadError = MutableLiveData<Boolean>()

    fun getVenues(): LiveData<List<Venue>?> = venues
    fun getError(): LiveData<Boolean?> = venuesLoadError
    fun getLoading(): LiveData<Boolean?> = loading

    fun findVenueById(id: String): Venue? = venues.value?.first { it.id == id }

    fun searchForPlaces(near: String, query: String, limit: Int = 20) {
        loading.value = true
        disposable?.add(
            Single.zip(
                apiClient.search(near, query, limit),
                savedVenuesDao.selectAllItems(),
                BiFunction<SearchApiResponse, List<SavedVenue>, List<Venue>>
                { apiResponse, saveVenues ->
                    saveVenues.forEach { saved ->
                        apiResponse.response.venues.filter { it.id == saved.venueId }.forEach { it.saved = true }
                    }
                    return@BiFunction apiResponse.response.venues
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Venue>>() {
                    override fun onSuccess(merged: List<Venue>) {
                        loading.value = false
                        venues.value = merged
                        venuesLoadError.value = merged.isEmpty()
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        venuesLoadError.value = true
                    }
                })
        )
    }

    fun updateSaveStatus(id: String, isSave: Boolean, updateCallback: (Boolean) -> Unit) {
        if (isSave) {
            disposable?.add(
                savedVenuesDao.deleteItem(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableCompletableObserver() {
                        override fun onComplete() {
                            findVenueById(id)?.let { it.saved = false }
                            updateCallback.invoke(true)
                        }

                        override fun onError(e: Throwable?) {
                            updateCallback.invoke(false)
                        }
                    })
            )
        } else {
            disposable?.add(
                savedVenuesDao.insertItem(SavedVenue(id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableCompletableObserver() {
                        override fun onComplete() {
                            findVenueById(id)?.let { it.saved = true }
                            updateCallback.invoke(true)
                        }

                        override fun onError(e: Throwable?) {
                            updateCallback.invoke(false)
                        }
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.clear()
        disposable = null
    }
}