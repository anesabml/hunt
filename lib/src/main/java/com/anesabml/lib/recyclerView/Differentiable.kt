package com.anesabml.lib.recyclerView

interface Differentiable {

    val diffId: String

    fun areContentsTheSame(other: Differentiable): Boolean = this == other

    fun getChangePayload(other: Differentiable): Any? = null

    companion object {

        fun fromCharSequence(charSequenceSupplier: () -> CharSequence): Differentiable {
            val id = charSequenceSupplier().toString()

            return object : Differentiable {
                override val diffId: String get() = id

                override fun equals(other: Any?): Boolean = id == other

                override fun hashCode(): Int = id.hashCode()
            }
        }
    }
}
