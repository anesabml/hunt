package com.anesabml.hunt.ui.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.FragmentPostsBinding
import com.anesabml.hunt.ui.posts.adapter.PostsPagerAdapter
import com.anesabml.lib.extension.viewBinding
import com.google.android.material.tabs.TabLayoutMediator

class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val binding by viewBinding(FragmentPostsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        with(binding) {
            viewPager.adapter = PostsPagerAdapter(
                childFragmentManager,
                lifecycle
            )

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.setText(R.string.popular)
                    1 -> tab.setText(R.string.newest)
                }
            }.attach()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PostsFragment()
    }
}
