package com.anesabml.hunt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val user: User,
    val body: String,
    val createdAt: String,
    val id: String
) : Parcelable
