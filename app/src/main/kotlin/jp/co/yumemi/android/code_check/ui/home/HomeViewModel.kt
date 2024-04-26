package jp.co.yumemi.android.code_check.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) :ViewModel() {


    val responseGithubRepositoryList = MutableLiveData<ResultState>()
    val gitHubRepositoryList= MutableLiveData<List<GithubRepositoryData>> ()


    init {
        // Initialize with the complete data
        gitHubRepositoryList.value = emptyList()
    }

    fun searchResults(inputText: String) {
        logMessage("Searching GitHub repositories with input: $inputText")
        viewModelScope.launch {
            try {
                val serverResponse = githubRepository.getGitHubAccountFromDataSource(inputText)
                    .firstOrNull()

                if (serverResponse != null) {
                    logMessage("Search results received: ${serverResponse.items.size} items")
                    responseGithubRepositoryList.value = ResultState.Success(serverResponse.items)
                }
            } catch (e: Exception) {
                logMessage("Error during repository_search: ${e.message}")

            }
        }
    }




    private fun logMessage(message: String) {
        Log.d("SearchViewModel", message)
    }
}