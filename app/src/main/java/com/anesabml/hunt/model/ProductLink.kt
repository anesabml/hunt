package com.anesabml.hunt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductLink(
    val type: String,
    val url: String
) : Parcelable
