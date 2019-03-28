package com.softsandr.placesearch.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.softsandr.placesearch.R
import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.di.InjectableActivity
import com.softsandr.placesearch.ui.details.DetailsActivity
import com.softsandr.placesearch.ui.viewmodel.ViewModelFactory
import com.softsandr.placesearch.utils.getQueryTextObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : InjectableActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var mapView: View
    private lateinit var fabButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var warningView: View
    private lateinit var viewModel: SearchViewModel

    private var showMap = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupToolbar()
        setupMeta()
        setupSearch()
        setupRecycler()
        setupMap()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupRecycler() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        recyclerView = findViewById(R.id.view_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchListAdapter(this, viewModel, selectCallback)

        observeViewModel()
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapView = mapFragment.view!!
        mapView.visibility = View.GONE

        fabButton = findViewById(R.id.activity_search_fab)
        fabButton.setOnClickListener {
            showMap = mapView.visibility != View.VISIBLE
            mapView.visibility = if (showMap) View.VISIBLE else View.GONE
            recyclerView.visibility = if (!showMap) View.VISIBLE else View.GONE
        }
        fabButton.hide()
    }

    private fun setupMeta() {
        errorView = findViewById(R.id.view_error_root)
        warningView = findViewById(R.id.view_warning_root)
        loadingView = findViewById(R.id.view_loading_root)
    }

    private fun setupSearch() {
        val searchView : SearchView = findViewById(R.id.activity_search_content_search_view)
        searchView.getQueryTextObservable()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { text -> !TextUtils.isEmpty(text) }
            .distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text -> viewModel.searchForPlaces(SEARCH_LOCATION, text) }
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
        map = googleMap
        map.setOnInfoWindowClickListener { marker: Marker? ->
            marker?.let {
                DetailsActivity.launch(this, marker.tag as String)
            }
        }
        addCenterMarker()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(SAVE_KEY_SHOW_MAP, showMap)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        showMap = savedInstanceState?.getBoolean(SAVE_KEY_SHOW_MAP, false) ?: false
    }

    private fun observeViewModel() {
        viewModel.getLoading().observe(this, Observer { loading ->
            loading?.let {
                loadingView.visibility = if (loading) View.VISIBLE else View.GONE
                if (loading) {
                    recyclerView.visibility = View.GONE
                    mapView.visibility = View.GONE
                    errorView.visibility = View.GONE
                    warningView.visibility = View.GONE
                    fabButton.hide()
                }
            }
        })

        viewModel.getError().observe(this, Observer { error ->
            error?.let {
                if (error) {
                    errorView.visibility = View.VISIBLE
                    warningView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    mapView.visibility = View.GONE
                    fabButton.hide()
                } else {
                    errorView.visibility = View.GONE
                    warningView.visibility = View.GONE
                    if (showMap) {
                        mapView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                    }
                    fabButton.show()
                }
            }
        })

        viewModel.getVenues().observe(this, Observer { venues ->
            errorView.visibility = View.GONE
            loadingView.visibility = View.GONE
            if (venues != null) {
                if (!venues.isEmpty()) {
                    if (showMap) {
                        mapView.visibility = View.VISIBLE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                    }
                    warningView.visibility = View.GONE
                    fabButton.show()
                    map.clear()
                    addCenterMarker()
                    venues.forEach { addSearchItemMarker(it) }
                }
            } else {
                recyclerView.visibility = View.GONE
                mapView.visibility = View.GONE
                warningView.visibility = View.VISIBLE
            }
        })
    }

    private fun addCenterMarker() {
        val sydney = LatLng(SEARCH_LOCATION_LAT, SEARCH_LOCATION_LNG)
        map.addMarker(MarkerOptions().position(sydney).title(getString(R.string.seattle_center)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
    }

    private fun addSearchItemMarker(venue: Venue) {
        venue.location?.let {
            val venueMarker = LatLng(it.lat, it.lng)
            val marker = map.addMarker(MarkerOptions().position(venueMarker).title(venue.name))
            marker.tag = venue.id
        }
    }

    private val selectCallback : (SearchListItem) -> Unit = { DetailsActivity.launch(this, it.id) }

    companion object {
        private const val SAVE_KEY_SHOW_MAP = "save_key_show_map"

        const val SEARCH_LOCATION = "Seattle,+WA"
        const val SEARCH_LOCATION_LAT = 47.60621
        const val SEARCH_LOCATION_LNG = -122.33207
    }
}
