package com.anesabml.hunt.model

import org.jetbrains.annotations.Nullable

data class Media(
    val type: String,
    val url: String,
    val videoUrl: @Nullable String?
)
