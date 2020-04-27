package com.anesabml.hunt.data.repository

import com.anesabml.hunt.data.dataSource.PostsDataSource
import com.anesabml.hunt.model.Post
import com.anesabml.lib.network.Result
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PostsRepositoryTest {

    private val mockDataSource = mockk<PostsDataSource>()
    private val SUT = PostsRepository(mockDataSource)

    @Test
    fun getPostDetails_emitsLoading() = runBlockingTest {
        clearAllMocks()

        // Given
        coEvery { mockDataSource.getPostDetails(any()) } returns flowOf(Result.Loading)

        // When
        val actual = SUT.getPostDetails("1")

        // Then
        coVerify { mockDataSource.getPostDetails("1") }

        actual.collect {
            assertThat(it).isEqualTo(Result.Loading)
        }
    }

    @Test
    fun getPostDetails_emitsPostDetails() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected =
            Post("id", "name", "tagline", "2020-01-01", "2020-01-01", null, false, 100)

        coEvery { mockDataSource.getPostDetails(any()) } returns flowOf(Result.Success(expected))

        // When
        val actual = SUT.getPostDetails("1")

        // Then
        coVerify { mockDataSource.getPostDetails("1") }

        actual.collect {
            assertThat(it).isEqualTo(Result.Success(expected))
        }
    }

    @Test
    fun getPostDetails_emitsError() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected = IOException("Couldn't Connect")

        coEvery { mockDataSource.getPostDetails(any()) } returns flowOf(
            Result.Error(
                expected
            )
        )

        // When
        val actual = SUT.getPostDetails("1")

        // Then
        coVerify { mockDataSource.getPostDetails("1") }

        actual.collect {
            assertThat(it).isEqualTo(Result.Error(expected))
        }
    }

    @Test
    fun getProductOfTheDay_returnsProduct() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected =
            Post("id", "name", "tagline", "2020-01-01", "2020-01-01", null, false, 100)

        coEvery { mockDataSource.getPostOfTheDay() } returns Result.Success(expected)

        // When
        val actual = SUT.getProductOfTheDay()

        // Then
        coVerify { mockDataSource.getPostOfTheDay() }

        assertThat(actual).isEqualTo(Result.Success(expected))
    }

    @Test
    fun getProductOfTheDay_returnsError() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected = IOException("Couldn't Connect")

        coEvery { mockDataSource.getPostOfTheDay() } returns Result.Error(expected)

        // When
        val actual = SUT.getProductOfTheDay()

        // Then
        coVerify { mockDataSource.getPostOfTheDay() }

        assertThat(actual).isEqualTo(Result.Error(expected))
    }
}
