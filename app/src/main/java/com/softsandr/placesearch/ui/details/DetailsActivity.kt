package com.softsandr.placesearch.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.softsandr.placesearch.R
import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.databinding.ActivityDetailsContentBinding
import com.softsandr.placesearch.di.InjectableActivity
import com.softsandr.placesearch.ui.search.SearchActivity
import com.softsandr.placesearch.ui.viewmodel.ViewModelFactory
import com.softsandr.placesearch.utils.NavigatingUtil.openExternalBrowser
import javax.inject.Inject

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
class DetailsActivity: InjectableActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: DetailsViewModel
    private var contentBinding: ActivityDetailsContentBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupMap()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        intent?.extras?.getString(INTENT_KEY_VENUE_ID)?.let { viewModel.getVenueDetails(it) }
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.view?.minimumHeight = resources.displayMetrics.heightPixels / 2
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val sydney = LatLng(SearchActivity.SEARCH_LOCATION_LAT, SearchActivity.SEARCH_LOCATION_LNG)
        map.addMarker(MarkerOptions().position(sydney).title(getString(R.string.seattle_center)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))

        viewModel.getVenue().observe(this, Observer { venue -> showDetails(venue) })
    }

    private fun showDetails(venue: Venue?) {
        if (venue != null) {
            venue.location?.let {
                val venueMarker = LatLng(it.lat, it.lng)
                map.addMarker(MarkerOptions().position(venueMarker).title(venue.name))
            }

            if (contentBinding == null) {
                contentBinding = DataBindingUtil.bind(findViewById(R.id.activity_details_content_root))
            }
            contentBinding?.item = DetailsViewItem.buildFromVenue(venue)
            contentBinding?.linkClickListener = View.OnClickListener { openExternalBrowser(this, venue.canonicalUrl) }
            contentBinding?.executePendingBindings()
        }
    }

    companion object {
        private const val INTENT_KEY_VENUE_ID = "intent_key_venue_id"

        @JvmStatic
        fun launch(context: Context, venueId: String) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(INTENT_KEY_VENUE_ID, venueId)
            context.startActivity(intent)
        }
    }
}