package com.techK.foodium.presentation.adapters.binding_adapters

import android.graphics.Paint
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.techK.foodium.R
import com.techK.foodium.data.response.ExtendedIngredient
import com.techK.foodium.domain.entities.Recipe
import org.jsoup.Jsoup

@BindingAdapter("setVisible")
fun Chip.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
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

@BindingAdapter("setJSoupText")
fun TextView.setJSoupText(input: String) {
    text = Jsoup.parse(input).text()
}

@BindingAdapter("setQuantity")
fun TextView.setQuantity(item: ExtendedIngredient) {
    val quantity = "${item.amount.toString().substring(0, 3)} ${item.unit}"
    text = quantity
}

@BindingAdapter("showRating")
fun RatingBar.showRating(item: Recipe) {
    rating = (item.healthScore / 10).toFloat()
}

@BindingAdapter("underline")
fun TextView.underline(item: String) {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}