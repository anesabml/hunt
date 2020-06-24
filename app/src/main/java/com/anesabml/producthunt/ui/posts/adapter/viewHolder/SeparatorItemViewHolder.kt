package com.anesabml.producthunt.ui.posts.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.anesabml.producthunt.databinding.ItemSeperatorBinding

class SeparatorItemViewHolder(private val binding: ItemSeperatorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(separatorText: String) {
        binding.description.text = separatorText
    }
}
