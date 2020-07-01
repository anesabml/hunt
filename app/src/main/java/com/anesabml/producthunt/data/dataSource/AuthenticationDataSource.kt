package com.anesabml.producthunt.data.dataSource

import com.anesabml.hunt.ViewerQuery
import com.anesabml.producthunt.api.ApiService
import com.anesabml.producthunt.extension.toUser
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.model.User
import com.anesabml.producthunt.utils.Constant
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(
    private val retrofitService: ApiService,
    private val apolloClient: ApolloClient
) {

    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Token {
        return retrofitService.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = Constant.REDIRECT_URI,
            grantType = "authorization_code",
            code = code
        )
    }

    suspend fun getCurrentUser(): User {
        val query = ViewerQuery()
        val response = apolloClient.query(query).toDeferred().await()
        return response.data?.viewer?.user?.toUser()
            ?: throw IllegalStateException("Response was null")

    }
}
