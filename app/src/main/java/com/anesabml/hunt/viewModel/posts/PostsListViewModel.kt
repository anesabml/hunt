package com.anesabml.hunt.viewModel.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.anesabml.hunt.data.repository.PostsRepository
import com.anesabml.hunt.model.Listing
import com.anesabml.hunt.model.PostEdge
import com.anesabml.hunt.viewModel.posts.PostsListFragment.Companion.ARG_SORT_BY
import com.anesabml.hunt.utils.DefaultDispatcherProvider
import com.anesabml.hunt.utils.DispatcherProvider
import com.anesabml.lib.network.Result
import com.anesabml.lib.viewModel.AssistedSavedStateViewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class PostsListViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    // must be inside of the ViewModel class!
    @AssistedInject.Factory
    interface Factory :
        AssistedSavedStateViewModelFactory<PostsListViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): PostsListViewModel
    }

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val sortBy = savedStateHandle.getLiveData<String>(ARG_SORT_BY)

    private val repoResult: MutableLiveData<Listing<PostEdge>> = MutableLiveData()

    val posts: LiveData<PagedList<PostEdge>> = repoResult.switchMap { it.pagedList }
    val results: LiveData<Result<Unit>> = repoResult.switchMap { it.networkState }
    val refreshState: LiveData<Result<Unit>> = repoResult.switchMap { it.refreshState }

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(dispatcherProvider.io()) {
            val result = postsRepository.getPosts(sortBy.value!!, PAGE_SIZE)
            repoResult.postValue(result)
        }
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }
}
