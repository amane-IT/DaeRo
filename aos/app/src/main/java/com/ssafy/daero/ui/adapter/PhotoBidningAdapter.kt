package com.ssafy.daero.ui.adapter

import android.view.RoundedCorner
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
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

@BindingAdapter("roundImg")
fun loadPreference(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.placeholder_trip_album)
        .transform(CenterCrop(), RoundedCorners(16))
        .into(view)
}
