package com.anesabml.hunt.ui.posts

import android.view.View
import com.anesabml.hunt.model.Post

interface PostsListInteractions {

    fun onClickPost(itemView: View, post: Post)
}
