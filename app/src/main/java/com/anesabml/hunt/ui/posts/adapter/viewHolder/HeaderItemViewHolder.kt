package com.anesabml.hunt.ui.posts.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.anesabml.hunt.databinding.ItemHeaderBinding
import com.anesabml.hunt.model.PostEdge
import com.anesabml.hunt.utils.DateUtils.stringDateToCalendar
import java.util.Calendar
import java.util.Locale

class HeaderItemViewHolder(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        postEdge: PostEdge
    ) {
        val calendar = stringDateToCalendar(postEdge.node.featuredAt ?: postEdge.node.createdAt)
        binding.headerTextView.text =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    }
}
