package jp.co.yumemi.android.code_check.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the HomeFragment responsible for managing data related to GitHub repository search.
 *
 * @property githubRepository The repository responsible for fetching GitHub repository data.
 * @property networkConnectivityRepository The repository responsible for monitoring network connectivity.
 * @property isOnline LiveData indicating the current internet connection status.
 * @property responseGithubRepositoryList LiveData holding the result state of GitHub repository search.
 * @property gitHubRepositoryList LiveData holding the list of GitHub repository data.
 * @property errorState LiveData holding the error state in case of network failures.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    connectivityRepository: ConnectivityRepository
) : ViewModel() {
    // LiveData to observe network connectivity status
    val isOnline = connectivityRepository.isConnected
        ?.map { it } // Use the Elvis operator to provide a default value
        ?.asLiveData()
        ?: MutableLiveData<Boolean>().apply { postValue(false) } // Provide a default value if networkConnectivityRepository.isConnected is null

    // LiveData to observe API response state
    val responseGithubRepositoryList = MutableLiveData<ResultState>()
    // LiveData to hold the list of GitHub repositories
    val gitHubRepositoryList = MutableLiveData<List<GithubRepositoryData>>()
    // LiveData to observe error state
    var errorState = MutableLiveData<ErrorState?>()
    val errorLiveData: MutableLiveData<ErrorState?> get() = errorState

    init {
        // Initialize with the complete data
        gitHubRepositoryList.value = emptyList()
    }

    /**
     * Searches GitHub repositories based on the provided input text.
     *
     * @param inputText The text to search for GitHub repositories.
     */
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

    /**
     * Clears the error state when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        errorState.value = null
        // Log when the ViewModel is cleared
        logMessage("ViewModel cleared")
    }

    /**
     * Helper function for logging messages with a specified tag.
     *
     * @param message The message to be logged.
     */
    private fun logMessage(message: String) {
        Log.d("SearchViewModel", message)
    }
}