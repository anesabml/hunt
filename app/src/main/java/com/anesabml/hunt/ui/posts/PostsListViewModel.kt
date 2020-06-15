package com.anesabml.hunt.ui.posts

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.anesabml.hunt.data.repository.PostsRepository
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.PostUiModel
import com.anesabml.hunt.ui.posts.PostsListFragment.Companion.ARG_SORT_BY
import com.anesabml.hunt.utils.DateUtils
import com.anesabml.lib.utils.DefaultDispatcherProvider
import com.anesabml.lib.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Locale

class PostsListViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val sortBy = savedStateHandle.getLiveData<String>(ARG_SORT_BY)

    val postsPagingFlow: Flow<PagingData<PostUiModel>> =
        postsRepository.getPostsStream(pageSize = PAGE_SIZE, sortBy = sortBy.value ?: "")
            .map { pagingData ->
                pagingData.map { PostUiModel.PostItem(it.node) }
            }
            .map { pagingData ->
                pagingData.insertSeparators { before, after ->
                    when {
                        before == null -> after?.post?.getDisplayDayOfWeek()?.let {
                            PostUiModel.SeparatorItem(it)
                        }
                        after == null -> null
                        shouldSeparate(before, after) -> after.post.getDisplayDayOfWeek()?.let {
                            PostUiModel.SeparatorItem(it)
                        }
                        else -> null
                    }
                }
            }
            .flowOn(dispatcherProvider.io()).cachedIn(viewModelScope)

    private fun shouldSeparate(
        before: PostUiModel.PostItem,
        after: PostUiModel.PostItem
    ): Boolean =
        before.post.getDayOfWeek() != after.post.getDayOfWeek()

}

private fun Post.getDayOfWeek() =
    DateUtils.stringDateToCalendar(featuredAt ?: createdAt)
        .get(Calendar.DAY_OF_WEEK)

private fun Post.getDisplayDayOfWeek() =
    DateUtils.stringDateToCalendar(featuredAt ?: createdAt)
        .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

