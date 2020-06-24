package com.anesabml.producthunt.data.dataSource

import androidx.paging.PagingSource
import com.anesabml.hunt.type.PostsOrder
import com.anesabml.producthunt.model.PostEdge
import com.apollographql.apollo.exception.ApolloException
import java.io.IOException

class PostsPagingSource(
    private val postsDataSource: PostsDataSource,
    sortBy: String
) :
    PagingSource<String, PostEdge>() {

    private val sortBy: PostsOrder = when (sortBy) {
        "RANKING" -> PostsOrder.RANKING
        else -> PostsOrder.NEWEST
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostEdge> {
        return try {
            val result = postsDataSource.getPosts(
                sortBy = sortBy,
                pageSize = params.loadSize,
                key = params.key
            )
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = result.last().cursor
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: ApolloException) {
            LoadResult.Error(exception)
        } catch (exception: IllegalStateException) {
            LoadResult.Error(exception)
        }
    }
}
