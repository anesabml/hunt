package com.anesabml.hunt.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.anesabml.hunt.CoroutineTestRule
import com.anesabml.hunt.data.repository.AuthenticationRepository
import com.anesabml.hunt.getOrAwaitValue
import com.anesabml.hunt.model.Token
import com.anesabml.lib.network.Result
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthenticationViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAuthenticationRepository = mockk<AuthenticationRepository>()
    private val mockSavedStateHandle = mockk<SavedStateHandle>()
    private val SUT =
        AuthenticationViewModel(
            mockSavedStateHandle,
            mockAuthenticationRepository,
            coroutineTestRule.testDispatcherProvider
        )

    @Test
    fun handleAuthorizationCode_returnsToken() {
        clearAllMocks()

        // Given
        val expectedToken = Token("token", "type")

        coEvery {
            mockAuthenticationRepository.retrieveAccessToken(
                any(),
                any(),
                any()
            )
        } returns Result.Success(expectedToken)

        // When
        SUT.handleAuthorizationCode("")

        // Then
        val tokenModel = SUT.result.getOrAwaitValue()

        assertThat(tokenModel).isEqualTo(Result.Success(expectedToken))
    }
}
