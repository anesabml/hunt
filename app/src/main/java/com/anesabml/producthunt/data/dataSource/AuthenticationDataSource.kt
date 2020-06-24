package com.anesabml.producthunt.data.dataSource

import com.anesabml.hunt.ViewerQuery
import com.anesabml.producthunt.api.ApiService
import com.anesabml.producthunt.extension.toUser
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.model.User
import com.anesabml.producthunt.utils.Constant
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
