package com.anesabml.hunt.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.ItemHeaderBinding
import com.anesabml.hunt.databinding.ItemPostBinding
import com.anesabml.hunt.model.PostEdge
import com.anesabml.hunt.ui.posts.PostsListInteractions
import com.anesabml.hunt.ui.posts.adapter.viewHolder.HeaderItemViewHolder
import com.anesabml.hunt.ui.posts.adapter.viewHolder.PostItemViewHolder
import com.anesabml.hunt.utils.DateUtils
import com.anesabml.lib.databinding.ItemNetworkStateBinding
import com.anesabml.lib.network.Result
import com.anesabml.lib.recyclerView.DiffAdapterCallback
import com.anesabml.lib.recyclerView.NetworkStateItemViewHolder
import com.anesabml.lib.recyclerView.StickyHeaderAdapter
import java.util.Calendar
import java.util.Locale

class PostsListRecyclerViewAdapter(
    private val postsListInteractions: PostsListInteractions,
    private val retryCallback: () -> Unit
) : PagedListAdapter<PostEdge, RecyclerView.ViewHolder>(DiffAdapterCallback<PostEdge>()),
    StickyHeaderAdapter<HeaderItemViewHolder> {

    private var networkState: Result<Unit>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.item_post -> {
                val binding =
                    ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostItemViewHolder(
                    binding
                )
            }
            R.layout.item_network_state -> {
                val binding =
                    ItemNetworkStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return NetworkStateItemViewHolder(
                    binding,
                    R.string.error_loading_posts,
                    retryCallback
                )
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_post ->
                // Must be not null because we are not using placeholders
                (holder as PostItemViewHolder).bind(postsListInteractions, getItem(position)!!)
            R.layout.item_network_state ->
                (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_post
        }
    }

    fun setNetworkState(newNetworkState: Result<Unit>?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != Result.Loading

    /**
     * Get the header id based on day of the week of each post
     */
    override fun getHeaderId(position: Int): Long {
        val item = getItem(position)!!.node
        return DateUtils.stringDateToCalendar(item.featuredAt ?: item.createdAt)
            .get(Calendar.DAY_OF_WEEK).toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderItemViewHolder {
        val binding =
            ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HeaderItemViewHolder(
            binding
        )
    }

    override fun onBindHeaderViewHolder(
        viewHolder: HeaderItemViewHolder,
        position: Int
    ) {
        viewHolder.bind(getItem(position)!!)
    }

    fun getHeaderAtPosition(position: Int): String {
        val postEdge = getItem(position)!!
        val calendar =
            DateUtils.stringDateToCalendar(postEdge.node.featuredAt ?: postEdge.node.createdAt)
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            ?: ""
    }
}
