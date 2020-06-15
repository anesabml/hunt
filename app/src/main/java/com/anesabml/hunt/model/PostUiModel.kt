package com.anesabml.hunt.model

import com.anesabml.lib.recyclerView.Differentiable

sealed class PostUiModel : Differentiable {
    data class PostItem(val post: Post) : PostUiModel() {
        override val diffId: String
            get() = post.id
    }

    data class SeparatorItem(val description: String) : PostUiModel() {
        override val diffId: String
            get() = description
    }
}