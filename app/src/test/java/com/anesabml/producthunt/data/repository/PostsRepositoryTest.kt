package com.anesabml.producthunt.data.repository

import com.anesabml.lib.network.Result
import com.anesabml.producthunt.CoroutineTestRule
import com.anesabml.producthunt.data.dataSource.PostsDataSource
import com.anesabml.producthunt.model.Post
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

@ExperimentalCoroutinesApi
class PostsRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val mockDataSource = mockk<PostsDataSource>()
    private val SUT = PostsRepository(mockDataSource, coroutineTestRule.testDispatcherProvider)

    @Test
    fun getPostDetails_ReturnsPostDetails_WhenSucceed() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expected =
                Post("id", "name", "tagline", "2020-01-01", "2020-01-01", null, false, 100)

            coEvery { mockDataSource.getPostDetails(any()) } returns expected

            // When
            val actual = SUT.getPostDetails("1")

            // Then
            coVerify { mockDataSource.getPostDetails("1") }
            Truth.assertThat(actual).isEqualTo(Result.Success(expected))
        }

    @Test
    fun getPostDetails_ReturnsError_WhenNetworkError() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expected = ApolloException("Couldn't Connect")

            coEvery { mockDataSource.getPostDetails(any()) } throws expected

            // When
            val actual = SUT.getPostDetails("1")

            // Then
            coVerify { mockDataSource.getPostDetails("1") }
            Truth.assertThat(actual).isEqualTo(Result.Error(expected))
        }

    @Test
    fun getProductOfTheDay_ReturnsProduct_WhenSucceed() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expected =
                Post("id", "name", "tagline", "2020-01-01", "2020-01-01", null, false, 100)

            coEvery { mockDataSource.getPostOfTheDay() } returns expected

            // When
            val actual = SUT.getProductOfTheDay()

            // Then
            coVerify { mockDataSource.getPostOfTheDay() }

            Truth.assertThat(actual).isEqualTo(Result.Success(expected))
        }

    @Test
    fun getProductOfTheDay_ReturnsError_WhenNetworkError() =
        coroutineTestRule.testCoroutineDispatcher.runBlockingTest {
            clearAllMocks()

            // Given
            val expected = ApolloException("Couldn't Connect")

            coEvery { mockDataSource.getPostOfTheDay() } throws expected

            // When
            val actual = SUT.getProductOfTheDay()

            // Then
            coVerify { mockDataSource.getPostOfTheDay() }

            Truth.assertThat(actual).isEqualTo(Result.Error(expected))
        }
}
