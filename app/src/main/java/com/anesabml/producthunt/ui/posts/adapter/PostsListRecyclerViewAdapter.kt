package com.anesabml.producthunt.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anesabml.producthunt.R
import com.anesabml.producthunt.databinding.ItemPostBinding
import com.anesabml.producthunt.databinding.ItemSeperatorBinding
import com.anesabml.producthunt.model.PostUiModel
import com.anesabml.producthunt.ui.posts.PostsListInteractions
import com.anesabml.producthunt.ui.posts.adapter.viewHolder.PostItemViewHolder
import com.anesabml.producthunt.ui.posts.adapter.viewHolder.SeparatorItemViewHolder
import com.anesabml.lib.recyclerView.DiffAdapterCallback

class PostsListRecyclerViewAdapter(
    private val postsListInteractions: PostsListInteractions
) : PagingDataAdapter<PostUiModel, RecyclerView.ViewHolder>(DiffAdapterCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_post -> {
                val binding =
                    ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostItemViewHolder(binding)
            }
            R.layout.item_seperator -> {
                val binding =
                    ItemSeperatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SeparatorItemViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postUiModel = getItem(position)
        postUiModel?.let {
            when (it) {
                is PostUiModel.PostItem ->
                    (holder as PostItemViewHolder).bind(it.post, postsListInteractions)
                is PostUiModel.SeparatorItem ->
                    (holder as SeparatorItemViewHolder).bind(it.description)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostUiModel.PostItem -> R.layout.item_post
            is PostUiModel.SeparatorItem -> R.layout.item_seperator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }
}
