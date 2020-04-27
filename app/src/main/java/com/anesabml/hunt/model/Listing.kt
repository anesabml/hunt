package com.anesabml.hunt.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.anesabml.lib.network.Result

/**
 * Data class that is necessary for a UI to show a listing
 */
data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<Result<Unit>>,
    val refreshState: LiveData<Result<Unit>>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)
