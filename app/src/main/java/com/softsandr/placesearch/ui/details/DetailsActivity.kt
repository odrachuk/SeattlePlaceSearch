package com.softsandr.placesearch.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.softsandr.placesearch.R
import com.softsandr.placesearch.di.InjectableActivity
import com.softsandr.placesearch.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
class DetailsActivity: InjectableActivity() {

    private lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)

        val text : TextView = findViewById(R.id.venue)
        val venueId = intent?.extras?.getString(INTENT_KEY_VENUE_ID)
        venueId?.let {
            viewModel.getVenue().observe(this, Observer { venue ->
                text.text = venue?.name ?: "N/A"
            })
            viewModel.getVenueDetails(it)
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