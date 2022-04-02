package com.payconiq.testApplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import com.payconiq.testApplication.GitHubUser
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.gitHubUserList.GitHubUserListViewModel
import com.payconiq.testApplication.utils.NetworkResult
import com.payconiq.testApplication.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GitHubUserListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    // Subject under test
    protected lateinit var viewModelTest: GitHubUserListViewModel

    @Mock
    private lateinit var repository: AppRepository

    @Mock
    private lateinit var responseObserver: Observer<NetworkResult<GitHubUser>>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModelTest = GitHubUserListViewModel(repository)
    }


    @Test
    fun `when fetching results ok then return a list successfully`() {
        testCoroutineRule.runBlockingTest {
            viewModelTest.response.observeForever(responseObserver)
            whenever(repository.getUserBySearchGitHub(anyString())).thenAnswer {
                NetworkResult.Success(responseObserver)
            }
            viewModelTest.fetchGitHubUser(anyString())
            assertNotNull(viewModelTest.response.value)
            assertEquals(NetworkResult.Success(responseObserver), viewModelTest.response.value)
        }
    }

    @Test
    fun checkLoadingState_OnRequestInit_isTrue() {
        viewModelTest.isLoading.value = true
        Truth.assertThat(viewModelTest.isLoading.value).isEqualTo(true)
    }


    @Test
    fun checkLoadingState_OnRequestComplete_isFalse() {
        viewModelTest.isLoading.value = false
        Truth.assertThat(viewModelTest.isLoading.value).isFalse()
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = Mockito.mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            viewModelTest.response.observeForever(responseObserver)
            Mockito.`when`(
                repository.getUserBySearchGitHub(
                    anyString()
                )
            ).thenReturn(NetworkResult.Error(exception))
            viewModelTest.fetchGitHubUser(anyString())
            assertEquals(null, viewModelTest.response.value)
        }
    }


    @After
    fun tearDown() {
        viewModelTest.response.removeObserver(responseObserver)
    }
}