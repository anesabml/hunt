package com.anesabml.hunt.data.repository

import com.anesabml.hunt.data.dataSource.AuthenticationDataSource
import com.anesabml.hunt.model.Token
import com.anesabml.lib.network.Result
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val dataSource: AuthenticationDataSource) {

    suspend fun retrieveAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<Token> {
        return dataSource.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getCurrentUser() = dataSource.getCurrentUser()
}
