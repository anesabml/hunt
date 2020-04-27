package com.anesabml.hunt.viewModel.posts

import android.view.View
import com.anesabml.hunt.model.Post

interface PostsListInteractions {

    fun onClickPost(itemView: View, post: Post)
}
