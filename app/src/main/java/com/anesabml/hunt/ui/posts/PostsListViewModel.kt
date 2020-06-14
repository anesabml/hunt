package com.anesabml.hunt.ui.posts

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
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
import com.anesabml.hunt.ui.posts.PostsListFragment.Companion.ARG_SORT_BY
import com.anesabml.hunt.utils.DefaultDispatcherProvider
import com.anesabml.hunt.utils.DispatcherProvider
import com.anesabml.lib.network.Result
import kotlinx.coroutines.launch

class PostsListViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

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
