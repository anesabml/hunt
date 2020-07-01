package com.anesabml.producthunt.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.anesabml.lib.network.Result
import com.anesabml.producthunt.CoroutineTestRule
import com.anesabml.producthunt.data.repository.AuthenticationRepository
import com.anesabml.producthunt.getOrAwaitValue
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.ui.auth.AuthenticationViewModel
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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
            mockAuthenticationRepository
        )

    @Test
    fun handleAuthorizationCode__ShouldPostValue_WhenTokenIsReturned() {
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
        coVerify {
            mockAuthenticationRepository.retrieveAccessToken(
                any(),
                any(),
                any()
            )
        }

        val actual = SUT.token.getOrAwaitValue()
        Truth.assertThat(actual).isEqualTo(Result.Success(expectedToken))
    }
}
