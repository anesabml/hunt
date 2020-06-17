package com.anesabml.hunt.data.dataSource

import com.anesabml.hunt.ViewerQuery
import com.anesabml.hunt.api.ApiService
import com.anesabml.hunt.extension.toUser
import com.anesabml.hunt.model.Token
import com.anesabml.hunt.model.User
import com.anesabml.hunt.utils.Constant
import com.anesabml.lib.network.Result
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import timber.log.Timber
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(
    private val retrofitService: ApiService,
    private val apolloClient: ApolloClient
) {

    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<Token> {
        return try {
            val tokenModel = retrofitService.getAccessToken(
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUri = Constant.REDIRECT_URI,
                grantType = "authorization_code",
                code = code
            )
            Result.Success(tokenModel)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.Error(throwable)
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        return try {
            val query = ViewerQuery()
            val response = apolloClient.query(query).toDeferred().await()
            val user = response.data?.viewer?.user?.toUser()
                ?: throw IllegalStateException("Response was null")
            Result.Success(user)
        } catch (exception: ApolloException) {
            Timber.e(exception)
            Result.Error(exception)
        } catch (exception: IllegalStateException) {
            Timber.e(exception)
            Result.Error(exception)
        }
    }
}
