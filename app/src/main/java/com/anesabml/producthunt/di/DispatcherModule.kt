package com.anesabml.producthunt.di

import com.anesabml.producthunt.utils.DefaultDispatcherProvider
import com.anesabml.producthunt.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DispatcherModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()
}
