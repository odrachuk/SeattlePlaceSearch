package com.softsandr.placesearch.ui.main

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.softsandr.placesearch.R
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.di.InjectableActivity
import javax.inject.Inject

class MainActivity : InjectableActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()

        apiClient.searchByCategory("Seattle,+WA", "coffee", 20)
            .subscribe({ response ->
                Log.d(TAG, "Response: $response")
            }, { throwable ->
                Log.e(TAG, "Error: $throwable")
            })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady")
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(47.60621, -122.33207)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Seattle"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
