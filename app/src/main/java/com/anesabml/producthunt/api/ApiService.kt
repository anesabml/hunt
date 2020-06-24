package com.anesabml.producthunt.api

import com.anesabml.producthunt.model.Token
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/v2/oauth/token")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("grant_type") grantType: String,
        @Query("code") code: String
    ): Token
}
