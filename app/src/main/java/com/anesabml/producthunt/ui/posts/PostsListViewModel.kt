package com.anesabml.producthunt.ui.posts

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.anesabml.producthunt.data.repository.PostsRepository
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.model.PostUiModel
import com.anesabml.producthunt.ui.posts.PostsListFragment.Companion.ARG_SORT_BY
import com.anesabml.producthunt.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Locale

class PostsListViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val postsRepository: PostsRepository
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
                pagingData.insertSeparators{ before, after ->
                    when {
                        before == null ->
                            getDayOfWeekName(after!!.post)?.let {
                                PostUiModel.SeparatorItem(it)
                            }
                        after == null -> null
                        shouldSeparate(before, after) ->
                            getDayOfWeekName(after.post)?.let {
                                PostUiModel.SeparatorItem(it)
                            }
                        else -> null
                    }
                }
            }
            .cachedIn(viewModelScope)

    private fun shouldSeparate(
        before: PostUiModel.PostItem,
        after: PostUiModel.PostItem
    ): Boolean {
        val beforeData =
            DateUtils.stringDateToCalendar(before.post.featuredAt ?: before.post.createdAt)
                .get(Calendar.DAY_OF_WEEK)
        val afterData =
            DateUtils.stringDateToCalendar(after.post.featuredAt ?: after.post.createdAt)
                .get(Calendar.DAY_OF_WEEK)
        return beforeData != afterData
    }

    private fun getDayOfWeekName(post: Post): String? {
        return DateUtils.stringDateToCalendar(post.featuredAt ?: post.createdAt)
            .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    }
}
