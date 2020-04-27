package com.anesabml.hunt.data.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anesabml.hunt.model.PostEdge
import com.apollographql.apollo.ApolloClient
import javax.inject.Inject

class PostsDataSourceFactory @Inject constructor(
    private val apolloClient: ApolloClient,
    private val sortBy: String
) : DataSource.Factory<String, PostEdge>() {
    val sourceLiveData = MutableLiveData<PostsKeyedDataSource>()
    override fun create(): DataSource<String, PostEdge> {
        val source = PostsKeyedDataSource(
            apolloClient,
            sortBy
        )
        sourceLiveData.postValue(source)
        return source
    }
}
