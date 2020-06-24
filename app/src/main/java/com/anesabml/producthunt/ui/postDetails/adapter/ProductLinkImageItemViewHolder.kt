package com.anesabml.producthunt.ui.postDetails.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.anesabml.lib.extension.hide
import com.anesabml.producthunt.databinding.ItemProductLinkImageBinding
import com.anesabml.producthunt.model.Media

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
