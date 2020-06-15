package com.anesabml.hunt.ui.postDetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.PostsRepository
import com.anesabml.hunt.model.Post
import com.anesabml.lib.utils.DefaultDispatcherProvider
import com.anesabml.lib.utils.DispatcherProvider
import com.anesabml.lib.network.Result

class PostDetailsViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val post = savedStateHandle.getLiveData<Post>("post")

    val postResult: LiveData<Result<Post>> = post.switchMap {
        postsRepository.getPostDetails(it.id)
            .asLiveData(viewModelScope.coroutineContext + dispatcherProvider.io())
    }
}
