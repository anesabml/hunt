package com.anesabml.producthunt.ui.postDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.anesabml.producthunt.databinding.ItemCommentBinding
import com.anesabml.producthunt.model.CommentEdge
import com.anesabml.lib.recyclerView.DiffAdapterCallback

class CommentsListRecyclerViewAdapter :
    ListAdapter<CommentEdge, CommentsListRecyclerViewAdapter.CommentItemViewHolder>(
        DiffAdapterCallback<CommentEdge>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder =
        CommentItemViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class CommentItemViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            commentEdge: CommentEdge
        ) {
            val comment = commentEdge.node
            with(binding) {
                userPhoto.load(comment.user.profileImage)
                userName.text = comment.user.name
                userHeadline.text = comment.user.headline
                commentBody.text = comment.body
            }
        }
    }
}
