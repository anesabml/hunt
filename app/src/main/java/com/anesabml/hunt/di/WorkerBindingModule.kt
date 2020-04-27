package com.anesabml.hunt.di

import com.anesabml.hunt.worker.ChildWorkerFactory
import com.anesabml.hunt.worker.PodNotificationWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(PodNotificationWorker::class)
    fun bindNotificationWorker(factory: PodNotificationWorker.Factory): ChildWorkerFactory
}
