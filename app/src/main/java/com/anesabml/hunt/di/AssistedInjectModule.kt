package com.anesabml.hunt.di

import androidx.work.ListenableWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
abstract class AssistedInjectModule

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)
