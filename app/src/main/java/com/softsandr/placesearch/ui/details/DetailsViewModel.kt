package com.softsandr.placesearch.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.api.DetailsApiResponse
import com.softsandr.placesearch.api.Venue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
class DetailsViewModel @Inject constructor(private val apiClient: ApiClient): ViewModel() {
    private var disposable: CompositeDisposable? = CompositeDisposable()
    private val loading = MutableLiveData<Boolean>()
    private val venue = MutableLiveData<Venue>()
    private val venueLoadError = MutableLiveData<Boolean>()

    fun getVenue(): LiveData<Venue?> = venue
    fun getError(): LiveData<Boolean?> = venueLoadError
    fun getLoading(): LiveData<Boolean?> = loading

    fun getVenueDetails(id: String) {
        venue.value = Venue(id, "Metroplex Town Cars",
            "https://foursquare.com/v/metroplex-town-cars/5ad1648a1987ec57f4601764",
            Venue.Contact("4252986295", "(425) 298-6295"),
            Venue.Location("32200 Military Rd S Apt B301",
                null,
                47.60372380771074,
                -122.33453392982481,
                "98001", "US", "Federal Way", "WA", "United States"),
            listOf(Venue.Category("54541b70498ea6ccd0204bff",
                "Transportation Service",
                Venue.Category.CategoryIcon("https://ss3.4sqi.net/img/categories_v2/travel/taxi_",
                    ".png"))))

//        loading.value = true
//        disposable?.add(apiClient.details(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeWith(object : DisposableSingleObserver<DetailsApiResponse>() {
//                override fun onSuccess(value: DetailsApiResponse) {
//                    loading.value = false
//                    venueLoadError.value = false
//                    venue.value = value.response.venue
//                }
//
//                override fun onError(e: Throwable) {
//                    loading.value = false
//                    venueLoadError.value = true
//                }
//            })
//        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.clear()
        disposable = null
    }
}