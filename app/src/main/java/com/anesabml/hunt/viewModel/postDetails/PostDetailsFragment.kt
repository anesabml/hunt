package com.anesabml.hunt.viewModel.postDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.FragmentPostDetailsBinding
import com.anesabml.hunt.extension.injector
import com.anesabml.hunt.model.CommentEdge
import com.anesabml.hunt.model.Media
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.model.ProductLink
import com.anesabml.hunt.viewModel.postDetails.adapter.CommentsListRecyclerViewAdapter
import com.anesabml.hunt.viewModel.postDetails.adapter.SliderViewPagerAdapter
import com.anesabml.lib.extension.hide
import com.anesabml.lib.extension.show
import com.anesabml.lib.extension.showSnackBar
import com.anesabml.lib.extension.viewBinding
import com.anesabml.lib.extension.viewModel
import com.anesabml.lib.network.Result
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.transition.MaterialContainerTransform

class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private val binding by viewBinding(FragmentPostDetailsBinding::bind)
    private val viewModel: PostDetailsViewModel by viewModel {
        injector.postDetailsViewModel.create(it)
    }
    private val args: PostDetailsFragmentArgs by navArgs()

    private val commentsListAdapter: CommentsListRecyclerViewAdapter =
        CommentsListRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            MaterialContainerTransform(requireContext()).apply {
                duration = resources.getInteger(R.integer.animation_duration).toLong()
            }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.post.name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardInformation.transitionName = args.post.id

        updateUi(args.post)

        viewModel.postResult.observe(viewLifecycleOwner) {
            updateState(it)
        }
    }

    private fun updateState(result: Result<Post>) {
        when (result) {
            Result.Loading -> {
                binding.progressBar.show()
            }
            is Result.Success -> {
                binding.progressBar.hide()
                updateUi(result.data)
            }
            is Result.Error -> {
                binding.progressBar.hide()
                view?.showSnackBar(R.string.error_loading_post_details)
            }
        }
    }

    private fun updateUi(post: Post) {
        with(binding) {
            setupViewPagerSlider(post.media)
            setupProductLinksChips(post.productLinks)
            setupCommentsList(post.comments)

            thumbnail.load(post.thumbnail!!.url) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }

            name.text = post.name
            tagline.text = post.tagline
            description.text = post.description
        }
    }

    private fun setupProductLinksChips(productLinks: List<ProductLink>) {
        productLinks.forEach { productLink ->
            addChipToGroup(productLink, binding.chipGroup)
        }
    }

    private fun setupViewPagerSlider(media: List<Media>) {
        val adapter = SliderViewPagerAdapter(requireContext(), media)
        binding.viewPagerMedia.adapter = adapter
    }

    private fun setupCommentsList(comments: List<CommentEdge>) {
        with(binding.commentsRecyclerView) {
            val commentsListAdapter = CommentsListRecyclerViewAdapter()
            adapter = commentsListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    RecyclerView.VERTICAL
                )
            )
            isNestedScrollingEnabled = false
            commentsListAdapter.submitList(comments)
        }
        binding.commentsTitle.show()
    }

    private fun addChipToGroup(productLink: ProductLink, chipGroup: ChipGroup) {
        val chip = Chip(context)
        chip.text = productLink.type
        chip.setOnClickListener {
            openLink(productLink.url)
        }

        chip.isClickable = true
        chip.isCheckable = false
        chip.isCloseIconVisible = false

        chipGroup.addView(chip)

        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PostDetailsFragment()
    }
}
