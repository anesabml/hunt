package com.anesabml.lib.recyclerView

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.anesabml.lib.databinding.ItemNetworkStateBinding
import com.anesabml.lib.network.Result

/**
 * ViewHolder used to show the network state.
 */
class NetworkStateItemViewHolder(
    private val binding: ItemNetworkStateBinding,
    @StringRes private val errorStringRes: Int,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(result: Result<Unit>?) {
        with(binding) {
            progressBar.visibility =
                toVisibility(
                    result is Result.Loading
                )
            retryButton.visibility =
                toVisibility(
                    result is Result.Error
                )
            errorMsg.visibility =
                toVisibility(
                    result is Result.Error
                )
            errorMsg.setText(errorStringRes)
        }
    }

    companion object {
        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
