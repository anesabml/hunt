package com.anesabml.producthunt.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.anesabml.producthunt.databinding.ItemLoadStateBinding
import com.anesabml.producthunt.ui.posts.adapter.viewHolder.PostsLoadStateViewHolder

class PostsLoadStateAdapter(private val retryCallback: () -> Unit) :
    LoadStateAdapter<PostsLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PostsLoadStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsLoadStateViewHolder(binding, retryCallback)
    }

    override fun onBindViewHolder(holder: PostsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
