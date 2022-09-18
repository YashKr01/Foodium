package com.techK.foodium.presentation.adapters.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("setVeganVisibility")
fun Chip.setVisibility(vegan: Boolean) {
    visibility = if (vegan) View.VISIBLE else View.GONE
}

@BindingAdapter("loadImage")
fun ShapeableImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("setIntText")
fun Chip.setIntText(query: Int) {
    text = query.toString()
}