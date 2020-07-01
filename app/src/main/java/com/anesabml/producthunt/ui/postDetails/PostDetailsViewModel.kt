package com.anesabml.producthunt.ui.postDetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.anesabml.lib.network.Result
import com.anesabml.producthunt.data.repository.PostsRepository
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.utils.DefaultDispatcherProvider
import com.anesabml.producthunt.utils.DispatcherProvider

class PostDetailsViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    companion object {
        const val POST_KEY = "post"
    }

    private val post = savedStateHandle.getLiveData<Post>(POST_KEY)

    val postResult: LiveData<Result<Post>> = post.switchMap {
        liveData(dispatcherProvider.io()) {
            emit(Result.Loading)
            emit(postsRepository.getPostDetails(it.id))
        }
    }
}
