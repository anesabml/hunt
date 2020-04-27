package com.anesabml.hunt.viewModel.postDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.anesabml.hunt.data.repository.PostsRepository
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.utils.DefaultDispatcherProvider
import com.anesabml.hunt.utils.DispatcherProvider
import com.anesabml.lib.network.Result
import com.anesabml.lib.viewModel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PostDetailsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    // must be inside of the ViewModel class!
    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<PostDetailsViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): PostDetailsViewModel
    }

    private val post = savedStateHandle.getLiveData<Post>("post")

    val postResult: LiveData<Result<Post>> = post.switchMap {
        postsRepository.getPostDetails(it.id)
            .asLiveData(viewModelScope.coroutineContext + dispatcherProvider.io())
    }
}
