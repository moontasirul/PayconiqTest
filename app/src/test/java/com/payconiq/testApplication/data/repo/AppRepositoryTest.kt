package com.payconiq.testApplication.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payconiq.testApplication.data.remote.apiService.IGitHubUserService
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.data.repository.BaseRepository.Companion.SOMETHING_WRONG
import com.payconiq.testApplication.utils.MockWebServerBaseTest
import com.payconiq.testApplication.utils.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class AppRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: AppRepository
    private lateinit var apiService: IGitHubUserService

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        apiService = provideTestApiService()
        repository = AppRepository(apiService)
    }

    @Test
    fun `given response ok when fetching results then return a list with elements`() {
        runBlocking {
            mockHttpResponse("GitHubUserSearchSuccessResponse.json", HttpURLConnection.HTTP_OK)
            val apiResponse = repository.getUserBySearchGitHub(anyString())

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.items?.size, 30)
        }
    }

    @Test
    fun `given response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse("GitHubUserSearchFailedResponse.json", HttpURLConnection.HTTP_OK)
            val apiResponse = repository.getUserBySearchGitHub(anyString())

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.items?.size, 0)
        }
    }


    @Test
    fun `given response failure when fetching results then return exception`() {
        runBlocking {
            mockHttpResponse(499)
            val apiResponse = repository.getUserBySearchGitHub(anyString())

            assertNotNull(apiResponse)
            val expectedValue = NetworkResult.Error(Exception(SOMETHING_WRONG))
            assertEquals(
                expectedValue.exception.message,
                (apiResponse as NetworkResult.Error).exception.message
            )
        }
    }
}