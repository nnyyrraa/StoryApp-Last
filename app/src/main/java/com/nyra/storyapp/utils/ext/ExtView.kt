package com.nyra.storyapp.utils.ext

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageUrl(url: String, isCenterCrop: Boolean) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.setImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}



