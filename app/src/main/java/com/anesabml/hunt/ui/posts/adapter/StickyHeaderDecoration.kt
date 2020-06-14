package com.anesabml.hunt.ui.posts.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.anesabml.hunt.ui.posts.adapter.viewHolder.HeaderItemViewHolder
import kotlin.math.max

/**
 * A sticky header decoration for RecyclerView.
 */
class StickyHeaderDecoration constructor(
    private val adapter: PostsListRecyclerViewAdapter
) : ItemDecoration() {

    private val headerCache: MutableMap<Long, HeaderItemViewHolder> = HashMap()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        var headerHeight = 0
        if (position != RecyclerView.NO_POSITION && showHeaderAboveItem(position)) {
            val header = getHeader(parent, position).itemView
            headerHeight += header.height
        }
        outRect.top = headerHeight
    }

    private fun showHeaderAboveItem(itemAdapterPosition: Int): Boolean {
        return if (itemAdapterPosition == 0) {
            true
        } else {
            adapter.getHeaderId(itemAdapterPosition - 1) !=
                adapter.getHeaderId(itemAdapterPosition)
        }
    }

    private fun getHeader(parent: RecyclerView, position: Int): RecyclerView.ViewHolder {
        val key = adapter.getHeaderId(position)
        return if (headerCache.containsKey(key)) {
            headerCache[key]!!
        } else {
            val viewHolder = adapter.onCreateHeaderViewHolder(parent)
            adapter.onBindHeaderViewHolder(viewHolder, position)
            val header = viewHolder.itemView
            // Specs for parent (RecyclerView)
            val widthSpec =
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
            val heightSpec =
                View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

            // Specs for children (headers)
            val childWidthSpec = ViewGroup.getChildMeasureSpec(
                widthSpec,
                parent.paddingLeft + parent.paddingRight,
                header.layoutParams.width
            )
            val childHeightSpec = ViewGroup.getChildMeasureSpec(
                heightSpec,
                parent.paddingTop + parent.paddingBottom,
                header.layoutParams.height
            )

            header.measure(childWidthSpec, childHeightSpec)
            header.layout(0, 0, header.measuredWidth, header.measuredHeight)
            headerCache[key] = viewHolder
            viewHolder
        }
    }

    override fun onDrawOver(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var previousHeaderId: Long = -1
        for (layoutPosition in 0 until parent.childCount) {
            val child = parent.getChildAt(layoutPosition)
            val adapterPosition = parent.getChildAdapterPosition(child)
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val headerId = adapter.getHeaderId(adapterPosition)
                if (headerId != previousHeaderId) {
                    previousHeaderId = headerId
                    val header = getHeader(parent, adapterPosition).itemView
                    canvas.save()
                    val top = getHeaderTop(parent, child, header, adapterPosition, layoutPosition)
                    canvas.translate(0f, top)
                    header.translationX = 0f
                    header.translationY = top
                    header.draw(canvas)
                    canvas.restore()
                }
            }
        }
    }

    private fun getHeaderTop(
        parent: RecyclerView,
        child: View,
        header: View,
        adapterPosition: Int,
        layoutPosition: Int
    ): Float {
        val headerHeight = header.height
        var top = child.y - headerHeight
        if (layoutPosition == 0) {
            val currentId = adapter.getHeaderId(adapterPosition)
            // find next view with header and compute the offscreen push if needed
            for (i in 1 until parent.childCount) {
                val adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i))
                if (adapterPosHere != RecyclerView.NO_POSITION) {
                    val nextId = adapter.getHeaderId(adapterPosHere)
                    if (nextId != currentId) {
                        val next = parent.getChildAt(i)
                        val offset = next.y - headerHeight
                        return if (offset < 0) {
                            offset
                        } else {
                            break
                        }
                    }
                }
            }
            top = max(0f, top)
        }
        return top
    }
}
