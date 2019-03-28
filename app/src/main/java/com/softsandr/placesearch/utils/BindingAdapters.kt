package com.softsandr.placesearch.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsandr.placesearch.R

object BindingAdapters {

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_texture_black_50dp)
                    .error(R.drawable.ic_texture_black_50dp)
            )
            .load(url)
            .into(imageView)
    }
}