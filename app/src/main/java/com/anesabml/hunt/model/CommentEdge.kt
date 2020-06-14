package com.anesabml.hunt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentEdge(
    val cursor: String,
    val node: Comment
) : Parcelable
