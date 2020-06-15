package com.anesabml.hunt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anesabml.hunt.data.dataSource.PostsDataSource
import com.anesabml.hunt.data.dataSource.PostsPagingSource
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostEdge
import com.anesabml.lib.network.Result
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

    fun getPostDetails(id: String): Flow<Result<Post>> =
        dataSource.getPostDetails(id)

    suspend fun getProductOfTheDay(): Result<Post> =
        dataSource.getPostOfTheDay()
}
