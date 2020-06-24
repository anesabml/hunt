package com.anesabml.producthunt.extension

import com.anesabml.hunt.PostQuery
import com.anesabml.hunt.PostsQuery
import com.anesabml.hunt.ViewerQuery
import com.anesabml.producthunt.model.Comment
import com.anesabml.producthunt.model.CommentEdge
import com.anesabml.producthunt.model.Media
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.model.PostEdge
import com.anesabml.producthunt.model.ProductLink
import com.anesabml.producthunt.model.Thumbnail
import com.anesabml.producthunt.model.User

fun PostsQuery.Edge.toPostEdge() =
    PostEdge(
        cursor,
        node.toPost()
    )

fun PostsQuery.Node.toPost() = Post(
    id,
    name,
    tagline,
    createdAt as String,
    featuredAt as String?,
    thumbnail?.toThumbnail(),
    isVoted,
    votesCount
)

fun PostsQuery.Thumbnail.toThumbnail() =
    Thumbnail(type, url, videoUrl)

fun PostQuery.Post.toPost() = Post(
    id,
    name,
    tagline,
    description,
    createdAt as String,
    featuredAt as String?,
    slug,
    thumbnail?.toThumbnail(),
    media.toMediaList(),
    productLinks.toProductLinkList(),
    url,
    isVoted,
    isCollected,
    reviewsRating,
    reviewsCount,
    votesCount,
    commentsCount,
    comments.toCommentList()
)

private fun List<PostQuery.Medium>.toMediaList(): List<Media> =
    this.map {
        it.toMedia()
    }

private fun List<PostQuery.ProductLink>.toProductLinkList(): List<ProductLink> =
    this.map {
        it.toProductLink()
    }

private fun PostQuery.ProductLink.toProductLink() =
    ProductLink(type, url)

private fun PostQuery.Medium.toMedia() =
    Media(type, url, videoUrl)

private fun PostQuery.Comments.toCommentList(): List<CommentEdge> =
    edges.map { it.toCommentEdge() }

private fun PostQuery.Edge.toCommentEdge(): CommentEdge =
    CommentEdge(cursor, node.toComment())

private fun PostQuery.Node.toComment(): Comment =
    with(fragments.commentInfo) {
        Comment(user.toUser(), body, createdAt.toString(), id)
    }

private fun PostQuery.User.toUser(): User =
    with(fragments.userInfo) {
        User(headline, profileImage, name)
    }

fun PostQuery.Thumbnail.toThumbnail(): Thumbnail =
    Thumbnail(type, url, videoUrl)

fun ViewerQuery.User.toUser(): User =
    User(id, coverImage, headline, isMaker, isViewer, profileImage, username, name)
