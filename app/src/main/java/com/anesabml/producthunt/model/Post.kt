package com.anesabml.producthunt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: String,
    val name: String,
    val tagline: String,
    val description: String?,
    val createdAt: String,
    val featuredAt: String?,
    val slug: String?,
    val thumbnail: Thumbnail?,
    val media: List<Media>,
    val productLinks: List<ProductLink>,
    val url: String?,
    val isVoted: Boolean,
    val isCollected: Boolean?,
    val reviewsRating: Double?,
    val reviewsCount: Int?,
    val votesCount: Int,
    val commentsCount: Int?,
    val comments: List<CommentEdge>
) : Parcelable {

    constructor(
        id: String,
        name: String,
        tagline: String,
        createdAt: String,
        featuredAt: String?,
        thumbnail: Thumbnail?,
        isVoted: Boolean,
        votesCount: Int
    ) : this(
        id,
        name,
        tagline,
        null,
        createdAt,
        featuredAt,
        null,
        thumbnail,
        listOf(),
        listOf(),
        null,
        isVoted,
        null,
        null,
        null,
        votesCount,
        null,
        listOf()
    )
}
