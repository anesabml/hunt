package com.anesabml.lib.recyclerView

interface Differentiable {

    val diffId: String

    fun areContentsTheSame(other: Differentiable): Boolean = this == other

    fun getChangePayload(other: Differentiable): Any? = null
    
}
