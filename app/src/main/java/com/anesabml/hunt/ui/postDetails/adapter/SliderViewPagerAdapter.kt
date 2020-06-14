package com.anesabml.hunt.ui.postDetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anesabml.hunt.databinding.ItemProductLinkImageBinding
import com.anesabml.hunt.model.Media

class SliderViewPagerAdapter(
    context: Context,
    private val mediaLinks: List<Media>
) : RecyclerView.Adapter<ProductLinkImageItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductLinkImageItemViewHolder {
        val binding =
            ItemProductLinkImageBinding.inflate(layoutInflater, parent, false)
        return ProductLinkImageItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductLinkImageItemViewHolder, position: Int) {
        holder.bind(mediaLinks[position])
    }

    override fun getItemCount(): Int = mediaLinks.size
}
