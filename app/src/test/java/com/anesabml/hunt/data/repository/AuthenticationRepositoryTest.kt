package com.anesabml.hunt.data.repository

import com.anesabml.hunt.data.dataSource.AuthenticationDataSource
import com.anesabml.hunt.model.Token
import com.anesabml.hunt.model.User
import com.anesabml.lib.network.Result
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class AuthenticationRepositoryTest {

    private val mockDataSource = mockk<AuthenticationDataSource>()
    private val SUT = AuthenticationRepository(mockDataSource)

    @Test
    fun retrieveAccessToken_returnsTokenModel() = runBlockingTest {
        clearAllMocks()

        // Given
        val expectedToken = Token("token", "type")

        coEvery { mockDataSource.getAccessToken(any(), any(), any()) } returns Result.Success(
            expectedToken
        )

        // When
        val actualResult =
            SUT.retrieveAccessToken("clientId", "clientSecret", "code")

        // Then
        coVerify {
            mockDataSource.getAccessToken("clientId", "clientSecret", "code")
        }

        assertThat(actualResult).isEqualTo(Result.Success(expectedToken))
    }

    @Test
    fun retrieveAccessToken_returnsException() = runBlockingTest {
        clearAllMocks()

        // Given
        val expectedException = IOException("Couldn't Connect")

        coEvery { mockDataSource.getAccessToken(any(), any(), any()) } returns Result.Error(
            expectedException
        )

        // When
        val actualResult =
            SUT.retrieveAccessToken("clientId", "clientSecret", "code")

        // Then
        coVerify {
            mockDataSource.getAccessToken("clientId", "clientSecret", "code")
        }

        assertThat(actualResult).isEqualTo(Result.Error(expectedException))
    }

    @Test
    fun getCurrentUser_returnsCurrentUser() = runBlockingTest {
        clearAllMocks()

        // Given
        val expectedUser =
            User("1", "image", "headline", false, true, "profileImage", "anes", "Anes")
        coEvery { mockDataSource.getCurrentUser() } returns Result.Success(expectedUser)

        // When
        val actualResult = SUT.getCurrentUser()

        // Then
        coVerify {
            mockDataSource.getCurrentUser()
        }

        assertThat(actualResult).isEqualTo(Result.Success(expectedUser))
    }

    @Test
    fun getCurrentUser_returnsException() = runBlockingTest {
        clearAllMocks()

        // Given
        val expectedException = IOException("Couldn't Connect")
        coEvery { mockDataSource.getCurrentUser() } returns Result.Error(expectedException)

        // When
        val actualResult = SUT.getCurrentUser()

        // Then
        coVerify {
            mockDataSource.getCurrentUser()
        }

        assertThat(actualResult).isEqualTo(Result.Error(expectedException))
    }
}
