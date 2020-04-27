package com.anesabml.hunt.data.dataSource

import PostQuery
import PostsQuery
import androidx.lifecycle.switchMap
import androidx.paging.Config
import androidx.paging.toLiveData
import com.anesabml.hunt.extension.toPost
import com.anesabml.hunt.extension.toPostEdge
import com.anesabml.hunt.model.Listing
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostEdge
import com.anesabml.lib.network.Result
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import type.PostsOrder

class PostsDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    fun getPosts(sortBy: String, pageSize: Int): Listing<PostEdge> {
        val sourceFactory =
            PostsDataSourceFactory(
                apolloClient,
                sortBy
            )

        // We use toLiveData Kotlin ext. function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            // we use Config Kotlin ext. function here, could also use PagedList.Config.Builder
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize * 2
            )
            // provide custom executor for network requests, otherwise it will default to
            // Arch Components' IO pool which is also used for disk access
//            fetchExecutor = networkExecutor
        )

        val refreshState = sourceFactory.sourceLiveData.switchMap {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = sourceFactory.sourceLiveData.switchMap {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
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
