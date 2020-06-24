package com.anesabml.producthunt.ui.posts

import android.view.View
import com.anesabml.producthunt.model.Post

interface PostsListInteractions {

    fun onClickPost(itemView: View, post: Post)
}
