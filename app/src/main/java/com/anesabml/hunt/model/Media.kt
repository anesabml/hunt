package com.anesabml.hunt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val type: String,
    val url: String,
    val videoUrl: String?
) : Parcelable
