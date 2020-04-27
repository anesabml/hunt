package com.anesabml.hunt.model

data class Comment(
    val user: User,
    val body: String,
    val createdAt: String,
    val id: String
)
