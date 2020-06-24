package com.anesabml.producthunt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anesabml.lib.network.Result
import com.anesabml.producthunt.data.dataSource.PostsDataSource
import com.anesabml.producthunt.data.dataSource.PostsPagingSource
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.model.PostEdge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val dataSource: PostsDataSource
) {

    fun getPostsStream(pageSize: Int, sortBy: String): Flow<PagingData<PostEdge>> =
        Pager(
            PagingConfig(pageSize = pageSize, enablePlaceholders = false)
        ) {
            PostsPagingSource(dataSource, sortBy)
        }.flow

    suspend fun getPostDetails(id: String): Result<Post> =
        dataSource.getPostDetails(id)

    suspend fun getProductOfTheDay(): Result<Post> =
        dataSource.getPostOfTheDay()
}
