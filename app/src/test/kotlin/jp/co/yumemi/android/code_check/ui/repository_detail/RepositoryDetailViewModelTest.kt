package jp.co.yumemi.android.code_check.ui.repository_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for [RepositoryDetailViewModel].
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryDetailViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock Observer for LiveData
    @Mock
    private lateinit var observer: Observer<GithubRepositoryData>

    // ViewModel under test
    private lateinit var viewModel: RepositoryDetailViewModel

    /**
     * Setup the ViewModel and mocks.
     */
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoryDetailViewModel()
    }

    /**
     * Test ViewModel initialization.
     */
    @Test
    fun `test ViewModel initialization`() {
        // Verify that the LiveData is initially empty
        assertEquals(null, viewModel.gitHubRepositoryDetails.value)
    }

    /**
     * Test search_history repository details.
     */
    @Test
    fun `test setRepositoryDetails`() {
        // Given
        val repositoryData = GithubRepositoryData(
            "RepoName",
            null,
            "Kotlin", "100", "50", "20", "5"
        )

        // When
        viewModel.setRepositoryDetails(repositoryData)

        // Then
        assertEquals(repositoryData, viewModel.gitHubRepositoryDetails.value)
    }

    /**
     * Test ViewModel's onCleared method.
     */
    @Test
    fun `test onCleared`() {
        // When
        viewModel.onCleared()

        // No assertion needed, just ensuring the method runs without error
    }
}