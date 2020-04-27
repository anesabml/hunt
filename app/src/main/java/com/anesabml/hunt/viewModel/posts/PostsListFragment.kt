package com.anesabml.hunt.viewModel.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.FragmentPostsListBinding
import com.anesabml.hunt.extension.injector
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.viewModel.posts.adapter.PostsListRecyclerViewAdapter
import com.anesabml.hunt.viewModel.posts.adapter.StickyHeaderDecoration
import com.anesabml.lib.extension.viewBinding
import com.anesabml.lib.extension.viewModel
import com.anesabml.lib.network.Result

class PostsListFragment : Fragment(R.layout.fragment_posts_list), PostsListInteractions {

    companion object {
        const val ARG_SORT_BY = "sort_by"

        @JvmStatic
        fun newInstance(sortBy: String) =
            PostsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SORT_BY, sortBy)
                }
            }
    }

    private val binding by viewBinding(FragmentPostsListBinding::bind)
    private lateinit var _adapter: PostsListRecyclerViewAdapter

    private val viewModel: PostsListViewModel by viewModel {
        injector.postsListViewModel.create(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshState.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = (it == Result.Loading)
        }
    }

    private fun setupRecyclerView() {
        with(binding.postsRecyclerView) {
            _adapter =
                PostsListRecyclerViewAdapter(this@PostsListFragment) {
                    viewModel.retry()
                }
            adapter = _adapter
            addItemDecoration(
                StickyHeaderDecoration(
                    _adapter
                )
            )
        }

        viewModel.posts.observe(viewLifecycleOwner) { _adapter.submitList(it) }

        viewModel.results.observe(viewLifecycleOwner) {
            _adapter.setNetworkState(it)
        }
    }

    override fun onClickPost(itemView: View, post: Post) {
        val extras = FragmentNavigatorExtras(itemView to post.id)
        val action =
            PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(post)
        findNavController().navigate(action, extras)
    }
}
