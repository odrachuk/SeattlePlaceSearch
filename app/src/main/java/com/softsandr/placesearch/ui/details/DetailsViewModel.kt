package com.softsandr.placesearch.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.api.DetailsApiResponse
import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.db.dao.SavedVenuesDao
import com.softsandr.placesearch.db.entity.SavedVenue
import com.softsandr.placesearch.utils.stubbedVenue
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
class DetailsViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val savedVenuesDao: SavedVenuesDao
) : ViewModel() {
    private var disposable: CompositeDisposable? = CompositeDisposable()
    private val venue = MutableLiveData<Venue>()

    fun getVenue(): LiveData<Venue?> = venue

    fun getVenueDetails(id: String) {
        disposable?.add(
            Single.zip(
                apiClient.details(id),
                savedVenuesDao.selectItem(id),
                BiFunction<DetailsApiResponse, Boolean, Venue>
                { apiResponse, itemNotFound ->
                    apiResponse.response.venue.saved = !itemNotFound
                    return@BiFunction apiResponse.response.venue
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Venue>() {
                    override fun onSuccess(value: Venue) {
                        venue.value = value
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )

//        disposable?.add(
//            Single.zip(
//                Single.fromCallable { stubbedVenue(id) },
//                savedVenuesDao.selectItem(id),
//                BiFunction<Venue?, Boolean, Venue>
//                { apiVenu, itemNotFound ->
//                    apiVenu.saved = !itemNotFound
//                    return@BiFunction apiVenu
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<Venue>() {
//                    override fun onSuccess(value: Venue) {
//                        venue.value = value
//                    }
//
//                    override fun onError(e: Throwable) {
//                    }
//                })
//        )
    }

    fun updateSaveStatus(id: String, isSave: Boolean, updateCallback: (Boolean) -> Unit) {
        if (isSave) {
            disposable?.add(
                savedVenuesDao.deleteItem(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableCompletableObserver() {
                        override fun onComplete() {
                            venue.value?.saved = false
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
                            venue.value?.saved = true
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