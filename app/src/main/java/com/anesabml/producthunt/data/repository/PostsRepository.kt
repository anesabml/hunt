package com.anesabml.producthunt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anesabml.lib.network.Result
import com.anesabml.producthunt.data.dataSource.PostsDataSource
import com.anesabml.producthunt.data.dataSource.PostsPagingSource
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.model.PostEdge
import com.anesabml.producthunt.utils.DefaultDispatcherProvider
import com.anesabml.producthunt.utils.DispatcherProvider
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val dataSource: PostsDataSource,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) {

    fun getPostsStream(pageSize: Int, sortBy: String): Flow<PagingData<PostEdge>> =
        Pager(
            PagingConfig(pageSize = pageSize, enablePlaceholders = false)
        ) {
            PostsPagingSource(dataSource, sortBy)
        }.flow.flowOn(dispatcherProvider.io())

    suspend fun getPostDetails(id: String): Result<Post> = withContext(dispatcherProvider.io()) {
        return@withContext try {
            val post = dataSource.getPostDetails(id)
            Result.Success(post)
        } catch (exception: ApolloException) {
            Timber.e(exception)
            Result.Error(exception)
        } catch (exception: IllegalStateException) {
            Timber.e(exception)
            Result.Error(exception)
        }
    }

    suspend fun getProductOfTheDay(): Result<Post> = withContext(dispatcherProvider.io()) {
        return@withContext try {
            val post = dataSource.getPostOfTheDay()
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
