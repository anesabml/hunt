package com.anesabml.hunt.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "access_token") var accessToken: String,
    @Json(name = "token_type") var type: String
)
