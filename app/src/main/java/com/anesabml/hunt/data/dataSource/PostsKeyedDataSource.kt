package com.anesabml.hunt.data.dataSource

import PostsQuery
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.anesabml.hunt.extension.toPostEdge
import com.anesabml.hunt.model.PostEdge
import com.anesabml.lib.network.Result
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import timber.log.Timber
import type.PostsOrder

class PostsKeyedDataSource(
    private val apolloClient: ApolloClient,
    sortBy: String
) :
    ItemKeyedDataSource<String, PostEdge>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    private val order: PostsOrder = when (sortBy) {
        "RANKING" -> PostsOrder.RANKING
        else -> PostsOrder.NEWEST
    }

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter and we don't support loadBefore
     * in this example.
     */
    val networkState = MutableLiveData<Result<Unit>>()
    val initialLoad = MutableLiveData<Result<Unit>>()
    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<PostEdge>
    ) {
        // set network value to loading.
        networkState.postValue(Result.Loading)
        initialLoad.postValue(Result.Loading)

        val postsQuery =
            PostsQuery.builder()
                .first(params.requestedLoadSize)
                .order(order)
                .build()

        apolloClient.query(postsQuery).enqueue(object : ApolloCall.Callback<PostsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Timber.e(e)
                retry = {
                    loadInitial(params, callback)
                }
                networkState.postValue(Result.Error(e))
                initialLoad.postValue(Result.Error(e))
            }

            override fun onResponse(response: Response<PostsQuery.Data>) {
                try {
                    val data = response.data ?: throw IllegalStateException("Response was null")
                    val result = data.posts().edges().map { it.toPostEdge() }
                    retry = null
                    callback.onResult(result)
                    networkState.postValue(Result.Success(Unit))
                    initialLoad.postValue(Result.Success(Unit))
                } catch (exception: IllegalStateException) {
                    Timber.e(exception)
                    networkState.postValue(Result.Error(exception))
                    initialLoad.postValue(Result.Error(exception))
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<PostEdge>) {
        // set network value to loading.
        networkState.postValue(Result.Loading)

        val postsQuery =
            PostsQuery.builder()
                .first(params.requestedLoadSize)
                .order(order)
                .after(params.key)
                .build()

        apolloClient.query(postsQuery).enqueue(object : ApolloCall.Callback<PostsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Timber.e(e)
                // keep a lambda for future retry
                retry = {
                    loadAfter(params, callback)
                }
                // publish the error
                networkState.postValue(Result.Error(e))
            }

            override fun onResponse(response: Response<PostsQuery.Data>) {
                try {
                    val data = response.data ?: throw IllegalStateException("Response was null")
                    val result = data.posts().edges().map { it.toPostEdge() }
                    // clear retry since last request succeeded
                    retry = null
                    networkState.postValue(Result.Success(Unit))
                    callback.onResult(result)
                } catch (exception: IllegalStateException) {
                    Timber.e(exception)
                    networkState.postValue(Result.Error(exception))
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<PostEdge>) {
        // ignored, since we only ever append to our initial load
    }

    override fun getKey(item: PostEdge): String =
        item.cursor
}
