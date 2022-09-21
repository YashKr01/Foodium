package com.techK.foodium.presentation.adapters.binding_adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.techK.foodium.R
import org.jsoup.Jsoup

@BindingAdapter("setVeganVisibility")
fun Chip.setVisibility(vegan: Boolean) {
    visibility = if (vegan) View.VISIBLE else View.GONE
}

@BindingAdapter("loadImage")
fun ShapeableImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_error_placeholder)
        .into(this)
}

@BindingAdapter("setIntText")
fun Chip.setIntText(query: Int) {
    text = query.toString()
}

@BindingAdapter("setJSoupText")
fun TextView.setJSoupText(input: String) {
    text = Jsoup.parse(input).text()
}