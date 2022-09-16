package com.techK.foodium.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyzedInstruction(
    @SerializedName("name")
    val name: String,
    @SerializedName("steps")
    val steps: List<Step>?,
) : Parcelable