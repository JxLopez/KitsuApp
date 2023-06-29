package com.jxlopez.kitsuapp.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jxlopez.kitsuapp.R

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_placeholder_image)
        .placeholder(R.drawable.ic_placeholder_image)
        .into(this)
}