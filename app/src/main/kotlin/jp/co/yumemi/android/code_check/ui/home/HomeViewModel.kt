package jp.co.yumemi.android.code_check.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.common.ResultState
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.repository.ConnectivityRepository
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.sources.local.LocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    private val localDataSource: LocalDataSource,
    connectivityRepository: ConnectivityRepository
) : ViewModel() {
    // Define a date format
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

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
        viewModelScope.launch {
            try {
                val serverResponse = githubRepository.getGitHubAccountFromDataSource(inputText)
                    .firstOrNull()

                if (serverResponse != null) {

                    responseGithubRepositoryList.value = ResultState.Success(serverResponse.items)
                }
            } catch (e: Exception) {

                errorState.postValue(ErrorState.Error("Network error occurred: ${e.message}"))
            }
        }
    }
    fun saveSearchQueryWithTimestamp(query: String) {
        val timestamp = System.currentTimeMillis()
        val formattedTimestamp = formatTimestamp(timestamp) // Format timestamp
        val searchHistory = SearchHistory(query = query, timestamp = formattedTimestamp)
        viewModelScope.launch {
            try {
                localDataSource.insert(searchHistory)
                Log.d("HomeViewModel", "Search history saved successfully: $query")
                // Retrieve the count of search history entries
                deleteOldestSearchHistory()
                Log.d("HomeViewModel", "Oldest search history entries deleted")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to save search history: $query, Error: ${e.message}")
            }
        }
    }
    private fun deleteOldestSearchHistory() {
        viewModelScope.launch {
            try {
                // Retrieve the count of search history entries
                val count = localDataSource.getSearchHistoryCount()
                Log.d("HomeViewModel", "Search history count: $count")
                // If there are more than 50 entries, delete the oldest entries
                if (count > 50) {
                    val oldestEntries = localDataSource.getNewestSearchHistory(count - 50)
                    val idsToDelete = oldestEntries.map { it.id }
                    localDataSource.deleteSearchHistory(idsToDelete)
                    Log.d("HomeViewModel", "Oldest search history entries deleted")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to delete oldest search history entries, Error: ${e.message}")
            }
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
        /**
     * Clears the error state when the ViewModel is cleared.
     */
    public override fun onCleared() {
        super.onCleared()
        errorState.value = null

    }

}