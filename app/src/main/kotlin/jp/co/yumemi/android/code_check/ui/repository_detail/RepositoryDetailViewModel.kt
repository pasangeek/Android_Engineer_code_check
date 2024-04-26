package jp.co.yumemi.android.code_check.ui.repository_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import javax.inject.Inject

/**
 * ViewModel responsible for managing the details of a GitHub repository.
 */
@HiltViewModel
class RepositoryDetailViewModel @Inject constructor() : ViewModel() {
    private val _githubRepositoryDetail = MutableLiveData<GithubRepositoryData>()

    val gitHubRepositoryDetails: LiveData<GithubRepositoryData>
        get() = _githubRepositoryDetail

    init {
        logMessage("RepositoryDetail ViewModel initialized")
    }

    /**
     * Sets the details of the GitHub repository.
     *
     * @param githubRepositoryData The data representing the GitHub repository.
     */
    fun setRepositoryDetails(githubRepositoryData: GithubRepositoryData) {
        _githubRepositoryDetail.value = githubRepositoryData
        logMessage("Repository details set: $githubRepositoryData")
    }

    /**
     * Clears the ViewModel when it's no longer needed.
     */
    override fun onCleared() {
        super.onCleared()
        logMessage("ViewModel cleared")
    }

    /**
     * Helper function for logging messages with a specified tag.
     *
     * @param message The message to be logged.
     */
    private fun logMessage(message: String) {
        Log.d("RepositoryDetailViewModel", message)
    }
}