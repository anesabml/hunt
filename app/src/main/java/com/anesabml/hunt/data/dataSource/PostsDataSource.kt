package com.anesabml.hunt.data.dataSource

import PostQuery
import PostsQuery
import com.anesabml.hunt.extension.toPost
import com.anesabml.hunt.extension.toPostEdge
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostEdge
import com.anesabml.lib.network.Result
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import type.PostsOrder
import javax.inject.Inject

class PostsDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getPosts(sortBy: PostsOrder, pageSize: Int, key: String?): List<PostEdge> {

        val postsQuery =
            PostsQuery.builder()
                .first(pageSize)
                .order(sortBy)
                .after(key)
                .build()

        val response = apolloClient.query(postsQuery).toDeferred().await()
        val data = response.data ?: throw IllegalStateException("Response was null")
        return data.posts().edges().map { it.toPostEdge() }
    }

    fun getPostDetails(id: String): Flow<Result<Post>> {
        return flow {
            try {
                emit(Result.Loading)
                val postQuery = PostQuery.builder().id(id).build()
                val response = apolloClient.query(postQuery).toDeferred().await()
                val data = response.data?.post() ?: throw IllegalStateException("Response was null")
                val post = data.toPost()
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
                PostsQuery.builder()
                    .first(1)
                    .order(PostsOrder.RANKING)
                    .build()

            val response = apolloClient.query(postsQuery).toDeferred().await()
            val data = response.data?.posts() ?: throw IllegalStateException("Response was null")
            val post = data.edges().first().toPostEdge().node
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
