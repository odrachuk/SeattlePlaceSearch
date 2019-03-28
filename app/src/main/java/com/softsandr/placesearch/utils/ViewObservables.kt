package com.softsandr.placesearch.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
fun SearchView.getQueryTextObservable() : Observable<String> {
    val subject = PublishSubject.create<String>()

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(text: String): Boolean {
            subject.onNext(text)
            return true
        }
    })

    return subject
}