package com.anesabml.producthunt.ui.posts.adapter.viewHolder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.anesabml.producthunt.databinding.ItemLoadStateBinding

class PostsLoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.buttonRetry.setOnClickListener {
            retryCallback()
        }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}
