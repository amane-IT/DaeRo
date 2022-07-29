package com.ssafy.daero.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}