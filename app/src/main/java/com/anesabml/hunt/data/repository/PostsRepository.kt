package com.anesabml.hunt.data.repository

import com.anesabml.hunt.data.dataSource.PostsDataSource
import com.anesabml.hunt.model.Listing
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostEdge
import com.anesabml.lib.network.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PostsRepository @Inject constructor(
    private val dataSource: PostsDataSource
) {

    fun getPosts(sortBy: String, pageSize: Int): Listing<PostEdge> =
        dataSource.getPosts(sortBy, pageSize)

    fun getPostDetails(id: String): Flow<Result<Post>> =
        dataSource.getPostDetails(id)

    suspend fun getProductOfTheDay(): Result<Post> =
        dataSource.getPostOfTheDay()
}
