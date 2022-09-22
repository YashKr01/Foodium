package com.techK.foodium.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val number: Int,
    val step: String,
) : Parcelable