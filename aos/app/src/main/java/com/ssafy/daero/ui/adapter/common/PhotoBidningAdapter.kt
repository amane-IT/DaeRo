package com.ssafy.daero.ui.adapter.common

import android.graphics.Color
import android.graphics.ColorFilter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R
import com.ssafy.daero.utils.view.setTint

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

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop().circleCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("likeState")
fun setLikeState(view: ImageView, likeYn: Char?) {
    likeYn?.let {
        if(it == 'y') {
            view.setImageResource(R.drawable.ic_like_full)
            view.clearColorFilter()
        } else {
            view.setImageResource(R.drawable.ic_like)
            view.setColorFilter(view.context.getColor(R.color.white))
        }
    }
}