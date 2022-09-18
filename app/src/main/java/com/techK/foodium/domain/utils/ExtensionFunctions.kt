package com.techK.foodium.domain.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ExtensionFunctions {

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun Context.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(this, id)

    fun View.scale(
        alpha: Float = 0f,
        scaleBy: Float = 0f,
        duration: Long = 0L,
        startingDelay: Long = 0,
    ) {
        animate().alpha(alpha).scaleX(scaleBy).scaleY(scaleBy)
            .setStartDelay(startingDelay).setDuration(duration).start()
    }

}