package com.anesabml.producthunt.ui.posts.adapter.viewHolder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.anesabml.producthunt.R
import com.anesabml.producthunt.databinding.ItemPostBinding
import com.anesabml.producthunt.model.Post
import com.anesabml.producthunt.ui.posts.PostsListInteractions

class PostItemViewHolder(
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        post: Post,
        postsListInteractions: PostsListInteractions
    ) {
        with(binding) {
            thumbnail.load(post.thumbnail?.url) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }
            name.text = post.name
            tagline.text = post.tagline
            votes.text = post.votesCount.toString()

            val animation = AnimationUtils.loadAnimation(
                root.context,
                R.anim.item_animation_from_bottom
            )
            root.animation = animation
            root.transitionName = post.id
            root.setOnClickListener {
                postsListInteractions.onClickPost(this.root, post)
            }
        }
    }
}
