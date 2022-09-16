package com.techK.foodium.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String,
) : Parcelable