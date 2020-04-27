package com.anesabml.hunt.di

import com.anesabml.hunt.utils.DefaultDispatcherProvider
import com.anesabml.hunt.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    @JvmStatic
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
