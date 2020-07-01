package com.anesabml.producthunt.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {

    fun main(): CoroutineContext = Dispatchers.Main
    fun io(): CoroutineContext = Dispatchers.IO
    fun default(): CoroutineContext = Dispatchers.Default
    fun unconfined(): CoroutineContext = Dispatchers.Unconfined
}

class DefaultDispatcherProvider :
    DispatcherProvider
