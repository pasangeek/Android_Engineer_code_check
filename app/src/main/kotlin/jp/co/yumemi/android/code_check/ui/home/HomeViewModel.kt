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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    connectivityRepository: ConnectivityRepository
) :ViewModel() {

    val isOnline = connectivityRepository.isConnected.asLiveData()
    val responseGithubRepositoryList = MutableLiveData<ResultState>()
    val gitHubRepositoryList= MutableLiveData<List<GithubRepositoryData>> ()

    var errorState = MutableLiveData<ErrorState?>()
    val errorLiveData: MutableLiveData<ErrorState?> get() = errorState
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

    override fun onCleared() {
        super.onCleared()
        errorState.value = null
        // Log when the ViewModel is cleared
        logMessage("ViewModel cleared")
    }


    private fun logMessage(message: String) {
        Log.d("SearchViewModel", message)
    }
}