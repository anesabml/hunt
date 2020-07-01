package com.anesabml.producthunt.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.anesabml.lib.network.Result
import com.anesabml.producthunt.CoroutineTestRule
import com.anesabml.producthunt.data.repository.AuthenticationRepository
import com.anesabml.producthunt.getOrAwaitValue
import com.anesabml.producthunt.model.User
import com.anesabml.producthunt.ui.main.MainViewModel
import com.google.common.truth.Truth
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockSavedStateHandle = mockk<SavedStateHandle>()
    private val mockAuthenticationRepository = mockk<AuthenticationRepository>()
    private val SUT =
        MainViewModel(
            mockSavedStateHandle,
            mockAuthenticationRepository
        )

    @Test
    fun getCurrentViewer_ShouldPostValue_WhenUserIsReturned() {
        clearAllMocks()

        // Given
        val expectedToken = User("Android developer", "profile image", "Anes")

        coEvery {
            mockAuthenticationRepository.getCurrentUser()
        } returns Result.Success(expectedToken)

        // When
        SUT.getCurrentViewer()

        // Then
        coVerify { mockAuthenticationRepository.getCurrentUser() }

        val actual = SUT.user.getOrAwaitValue()
        Truth.assertThat(actual).isEqualTo(Result.Success(expectedToken))
    }
}