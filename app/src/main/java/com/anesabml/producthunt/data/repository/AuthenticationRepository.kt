package com.anesabml.producthunt.data.repository

import com.anesabml.producthunt.data.dataSource.AuthenticationDataSource
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.model.User
import com.anesabml.lib.network.Result
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val dataSource: AuthenticationDataSource) {

    suspend fun retrieveAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<Token> =
        dataSource.getAccessToken(clientId, clientSecret, code)

    suspend fun getCurrentUser(): Result<User> =
        dataSource.getCurrentUser()
}
