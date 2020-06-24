package com.anesabml.producthunt.data.repository

import com.anesabml.producthunt.data.dataSource.PostsDataSource
import com.anesabml.producthunt.model.Post
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
class PostsRepositoryTest {

    private val mockDataSource = mockk<PostsDataSource>()
    private val SUT = PostsRepository(mockDataSource)

    @Test
    fun getPostDetails_returnsPostDetails() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected =
            Post("id", "name", "tagline", "2020-01-01", "2020-01-01", null, false, 100)

        coEvery { mockDataSource.getPostDetails(any()) } returns Result.Success(expected)

        // When
        val actual = SUT.getPostDetails("1")

        // Then
        coVerify { mockDataSource.getPostDetails("1") }
        assertThat(actual).isEqualTo(Result.Success(expected))
    }

    @Test
    fun getPostDetails_returnsError() = runBlockingTest {
        clearAllMocks()

        // Given
        val expected = IOException("Couldn't Connect")

        coEvery { mockDataSource.getPostDetails(any()) } returns Result.Error(expected)

        // When
        val actual = SUT.getPostDetails("1")

        // Then
        coVerify { mockDataSource.getPostDetails("1") }
        assertThat(actual).isEqualTo(Result.Error(expected))
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
