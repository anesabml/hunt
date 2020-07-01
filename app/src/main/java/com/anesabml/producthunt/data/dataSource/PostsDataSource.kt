package com.anesabml.producthunt.data.dataSource

import com.anesabml.hunt.PostQuery
import com.anesabml.hunt.PostsQuery
import com.anesabml.hunt.type.PostsOrder
import com.anesabml.producthunt.extension.toPost
import com.anesabml.producthunt.extension.toPostEdge
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.model.PostEdge
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import javax.inject.Inject

class PostsDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun getPosts(sortBy: PostsOrder, pageSize: Int, key: String?): List<PostEdge> {
        val postsQuery =
            PostsQuery(Input.optional(pageSize), Input.optional(sortBy), Input.optional(key))

        val response = apolloClient.query(postsQuery).toDeferred().await()
        val data = response.data ?: throw IllegalStateException("Response was null")
        return data.posts.edges.map { it.toPostEdge() }
    }

    suspend fun getPostDetails(id: String): Post {
        val postQuery = PostQuery(Input.optional(id))
        val response = apolloClient.query(postQuery).toDeferred().await()
        return response.data?.post?.toPost()
            ?: throw IllegalStateException("Response was null")

    }

    suspend fun getPostOfTheDay(): Post {
        val postsQuery =
            PostsQuery(Input.optional(1), Input.optional(PostsOrder.RANKING))

        val response = apolloClient.query(postsQuery).toDeferred().await()
        return response.data?.posts?.edges?.first()?.toPostEdge()?.node
            ?: throw IllegalStateException("Response was null")
    }
}
