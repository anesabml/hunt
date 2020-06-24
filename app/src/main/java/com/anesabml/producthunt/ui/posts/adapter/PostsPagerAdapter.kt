package com.anesabml.producthunt.ui.posts.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anesabml.producthunt.ui.posts.PostsListFragment

class PostsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val sortBy = when (position) {
            0 -> "RANKING"
            else -> "NEWEST"
        }
        return PostsListFragment.newInstance(sortBy)
    }
}
