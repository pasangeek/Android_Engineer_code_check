package jp.co.yumemi.android.code_check.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import jp.co.yumemi.android.code_check.data.model.Owner
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.remote.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for [HomeViewModel].
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{
    // Coroutine dispatcher for testing
    private val testDispatcher = TestCoroutineDispatcher()
    // Rule to swap the background executor used by
    // the Architecture Components
    // with a different one that executes each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    // Mock dependencies
    @Mock
    private lateinit var githubRepository: GithubRepository
    // Mock the dependencies
    @Mock
    private lateinit var connectivityRepository: ConnectivityRepository

    private lateinit var viewModel: HomeViewModel
    /**
     * Set up the test environment.
     */
    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this)
        // Set main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)
        Mockito.`when`(connectivityRepository.isConnected)
            .thenReturn(null)
        // Initialize ViewModel
        viewModel = HomeViewModel(githubRepository, connectivityRepository)
    }
    /**
     * Test that isOnline is false when networkConnectivityRepository returns null.
     */
    @Test
    fun `isOnline is false when networkConnectivityRepository returns null`() {
        // When
        val isOnlineValue = viewModel.isOnline.value

        // Then
        Assert.assertFalse(isOnlineValue ?: true)
    }
    /**
     * Test that searchResults updates responseGithubRepositoryList with successful response.
     */
    @Test
    fun `searchResults updates responseGithubRepositoryList with successful response`() {
        // Given
        val inputText = "android"
        val serverResponse = GithubServerResponse(
            1, false,
            listOf(
                GithubRepositoryData(
                    "AndroidRepo",
                    Owner("https://example.com/avatar.jpg"),
                    "Kotlin", "100", "50", "20", "5"
                )
            )
        )
        runBlocking {
            Mockito.`when`(githubRepository.getGitHubAccountFromDataSource(inputText))
                .thenReturn(flowOf(serverResponse))

            // When
            viewModel.searchResults(inputText)

            // Then
            Assert.assertEquals(ResultState.Success(serverResponse.items), viewModel.responseGithubRepositoryList.value)
        }
    }
    /**
     * Test that onCleared clears errorState.
     */
    @Test
    fun `onCleared clears errorState`() {
        // Given
        viewModel.errorState.value = ErrorState.Error("Some error")

        // When
        viewModel.onCleared()

        // Then
        Assert.assertNull(viewModel.errorState.value)
    }

    @Test
    fun `test searchResults error`() = runBlockingTest {
        // Given
        val exception = RuntimeException("Some network error")
        `when`(githubRepository.getGitHubAccountFromDataSource("query")).thenThrow(exception)

        // When
        viewModel.searchResults("query")

        // Then
        assertTrue(viewModel.errorLiveData.value is ErrorState.Error)
    }
}