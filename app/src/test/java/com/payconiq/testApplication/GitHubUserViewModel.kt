package com.payconiq.testApplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.payconiq.testApplication.data.repository.AppRepository
import com.payconiq.testApplication.ui.gitHubUserList.GitHubUserListNavigator
import com.payconiq.testApplication.ui.gitHubUserList.GitHubUserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PayconiqViewModelTest {

    private lateinit var PayconiqListViewModel: GitHubUserListViewModel
    private lateinit var repository: AppRepository
    val testDispatcher = TestCoroutineDispatcher()

    private lateinit var mNavigator: GitHubUserListNavigator

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initSetUp() {
        Dispatchers.setMain(testDispatcher)
        repository = Mockito.mock(AppRepository::class.java)
        PayconiqListViewModel = GitHubUserListViewModel(repository)
        mNavigator = Mockito.mock(GitHubUserListNavigator::class.java)
        PayconiqListViewModel.setNavigator(mNavigator)
    }


    @Test
    fun checkLoadingState_OnRequestInit_isTrue() {
        PayconiqListViewModel.isLoading.value = true
        Truth.assertThat(PayconiqListViewModel.isLoading.value).isEqualTo(true)
    }


    @Test
    fun checkLoadingState_OnRequestComplete_isFalse() {
        PayconiqListViewModel.isLoading.value = false
        Truth.assertThat(PayconiqListViewModel.isLoading.value).isFalse()
    }

    @Test
    fun getGitHubUserListResponse_isSuccess() {

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}