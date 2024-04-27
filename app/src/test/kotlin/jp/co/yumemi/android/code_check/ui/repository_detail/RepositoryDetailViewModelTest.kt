package jp.co.yumemi.android.code_check.ui.repository_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.data.model.Owner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<GithubRepositoryData>

    private lateinit var viewModel: RepositoryDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepositoryDetailViewModel()
    }

    @Test
    fun `test ViewModel initialization`() {
        // Verify that the LiveData is initially empty
        assertEquals(null, viewModel.gitHubRepositoryDetails.value)
    }




}