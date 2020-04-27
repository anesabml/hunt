package com.anesabml.hunt.viewModel.postDetails.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.anesabml.hunt.databinding.ItemProductLinkImageBinding
import com.anesabml.hunt.model.Media
import com.anesabml.lib.extension.hide

class ProductLinkImageItemViewHolder(private val binding: ItemProductLinkImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(media: Media) {
        binding.imageView.load(media.url) {
            crossfade(true)
            listener { _, _ ->
                binding.progressBar.hide()
            }
        }
    }
}
