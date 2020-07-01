package com.anesabml.producthunt.data.repository

import com.anesabml.lib.network.Result
import com.anesabml.producthunt.data.dataSource.AuthenticationDataSource
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.model.User
import com.anesabml.producthunt.utils.DefaultDispatcherProvider
import com.anesabml.producthunt.utils.DispatcherProvider
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val dataSource: AuthenticationDataSource,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) {

    suspend fun retrieveAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<Token> = withContext(dispatcherProvider.io()) {
        return@withContext try {
            val tokenModel = dataSource.getAccessToken(clientId, clientSecret, code)
            Result.Success(tokenModel)
        } catch (exception: IOException) {
            Timber.e(exception)
            Result.Error(exception)
        } catch (exception: HttpException) {
            Timber.e(exception)
            Result.Error(exception)
        }
    }

    suspend fun getCurrentUser(): Result<User> = withContext(dispatcherProvider.io()) {
        return@withContext try {
            val user = dataSource.getCurrentUser()
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
