package com.example.foodium.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val number: Int,
    val step: String
) : Parcelable