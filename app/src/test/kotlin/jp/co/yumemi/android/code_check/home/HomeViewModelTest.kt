package jp.co.yumemi.android.code_check.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{
    // Coroutine dispatcher for testing
    private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var githubRepository: GithubRepository

    // Mock the dependencies
    @Mock
    private lateinit var connectivityRepository: ConnectivityRepository

    private lateinit var viewModel: HomeViewModel
    /*  @Mock
      private lateinit var mockObserver: Observer<List<GithubRepositoryData>>
  */
    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this)
        // Set main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)
        Mockito.`when`(connectivityRepository.isConnected)
            .thenReturn(null)
        viewModel = HomeViewModel(githubRepository, connectivityRepository)
    }

    @Test
    fun `isOnline is false when networkConnectivityRepository returns null`() {
        // When
        val isOnlineValue = viewModel.isOnline.value

        // Then
        Assert.assertFalse(isOnlineValue ?: true)
    }



}