package com.anesabml.hunt.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.anesabml.hunt.data.repository.PostsRepository
import com.anesabml.hunt.utils.MyNotificationManager
import com.anesabml.lib.network.Result as NetworkResult
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PodNotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: PostsRepository
) : CoroutineWorker(appContext, workerParams) {

    private val notificationManager: MyNotificationManager = MyNotificationManager(appContext)

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory {
        override fun create(appContext: Context, workerParams: WorkerParameters): ListenableWorker
    }

    override suspend fun doWork(): Result {
        return when (val result = repository.getProductOfTheDay()) {
            is NetworkResult.Success -> {
                notificationManager.showPostNotification(result.data)
                Result.success()
            }
            else -> Result.failure()
        }
    }
}
