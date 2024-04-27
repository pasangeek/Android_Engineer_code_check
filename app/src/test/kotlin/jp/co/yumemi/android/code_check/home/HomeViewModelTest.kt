package jp.co.yumemi.android.code_check.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.GithubServerResponse
import jp.co.yumemi.android.code_check.data.model.Owner
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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

    @Test
    fun `onCleared clears errorState`() {
        // Given
        viewModel.errorState.value = ErrorState.Error("Some error")

        // When
        viewModel.onCleared()

        // Then
        Assert.assertNull(viewModel.errorState.value)
    }
}