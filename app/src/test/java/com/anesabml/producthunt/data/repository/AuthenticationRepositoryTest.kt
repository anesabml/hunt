package com.anesabml.producthunt.data.repository

import com.anesabml.lib.network.Result
import com.anesabml.producthunt.CoroutineTestRule
import com.anesabml.producthunt.data.dataSource.AuthenticationDataSource
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.model.User
import com.apollographql.apollo.exception.ApolloException
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class AuthenticationRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val mockDataSource = mockk<AuthenticationDataSource>()
    private val SUT =
        AuthenticationRepository(mockDataSource, coroutineTestRule.testDispatcherProvider)

    @Test
    fun retrieveAccessToken_ReturnsTokenModel_WhenSucceed() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expectedToken = Token("token", "type")

            coEvery { mockDataSource.getAccessToken(any(), any(), any()) } returns expectedToken


            // When
            val actualResult =
                SUT.retrieveAccessToken("clientId", "clientSecret", "code")

            // Then
            coVerify {
                mockDataSource.getAccessToken("clientId", "clientSecret", "code")
            }

            Truth.assertThat(actualResult).isEqualTo(Result.Success(expectedToken))
        }

    @Test
    fun retrieveAccessToken_ReturnsException_When_NetworkError() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expectedException = IOException("Couldn't Connect")

            coEvery { mockDataSource.getAccessToken(any(), any(), any()) } throws expectedException

            // When
            val actualResult =
                SUT.retrieveAccessToken("clientId", "clientSecret", "code")

            // Then
            coVerify {
                mockDataSource.getAccessToken("clientId", "clientSecret", "code")
            }

            Truth.assertThat(actualResult).isEqualTo(Result.Error(expectedException))
        }

    @Test
    fun getCurrentUser_ReturnsCurrentUser_WhenSucceed() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expectedUser =
                User("1", "image", "headline", false, true, "profileImage", "anes", "Anes")
            coEvery { mockDataSource.getCurrentUser() } returns expectedUser

            // When
            val actualResult = SUT.getCurrentUser()

            // Then
            coVerify {
                mockDataSource.getCurrentUser()
            }

            Truth.assertThat(actualResult).isEqualTo(Result.Success(expectedUser))
        }

    @Test
    fun getCurrentUser_ReturnsError_WhenNetworkError() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expectedException = ApolloException("Couldn't Connect")
            coEvery { mockDataSource.getCurrentUser() } throws expectedException

            // When
            val actualResult = SUT.getCurrentUser()

            // Then
            coVerify {
                mockDataSource.getCurrentUser()
            }

            Truth.assertThat(actualResult).isEqualTo(Result.Error(expectedException))
        }

    @Test
    fun getCurrentUser_ReturnsError_WhenParsingError() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expectedException = IllegalStateException("Response was null")
            coEvery { mockDataSource.getCurrentUser() } throws expectedException

            // When
            val actualResult = SUT.getCurrentUser()

            // Then
            coVerify {
                mockDataSource.getCurrentUser()
            }

            Truth.assertThat(actualResult).isEqualTo(Result.Error(expectedException))
        }
}
