package com.anesabml.hunt.data.dataSource

import com.anesabml.hunt.PostQuery
import com.anesabml.hunt.PostsQuery
import com.anesabml.hunt.extension.toPost
import com.anesabml.hunt.extension.toPostEdge
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostEdge
import com.anesabml.hunt.type.PostsOrder
import com.anesabml.lib.network.Result
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class PostsDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getPosts(sortBy: PostsOrder, pageSize: Int, key: String?): List<PostEdge> {
        val postsQuery =
            PostsQuery(Input.optional(pageSize), Input.optional(sortBy), Input.optional(key))

        val response = apolloClient.query(postsQuery).toDeferred().await()
        val data = response.data ?: throw IllegalStateException("Response was null")
        return data.posts.edges.map { it.toPostEdge() }
    }

    fun getPostDetails(id: String): Flow<Result<Post>> {
        return flow {
            try {
                emit(Result.Loading)
                val postQuery = PostQuery(Input.optional(id))
                val response = apolloClient.query(postQuery).toDeferred().await()
                val post = response.data?.post?.toPost() ?: throw IllegalStateException("Response was null")
                emit(Result.Success(post))
            } catch (exception: ApolloException) {
                Timber.e(exception)
                emit(Result.Error(exception))
            } catch (exception: IllegalStateException) {
                Timber.e(exception)
                emit(Result.Error(exception))
            }
        }
    }

    suspend fun getPostOfTheDay(): Result<Post> {
        return try {
            val postsQuery =
                PostsQuery(Input.optional(1), Input.optional(PostsOrder.RANKING))

            val response = apolloClient.query(postsQuery).toDeferred().await()
            val post = response.data?.posts?.edges?.first()?.toPostEdge()?.node
                ?: throw IllegalStateException("Response was null")
            Result.Success(post)
        } catch (exception: ApolloException) {
            Timber.e(exception)
            Result.Error(exception)
        } catch (exception: IllegalStateException) {
            Timber.e(exception)
            Result.Error(exception)
        }
    }
}
