package jp.co.yumemi.android.code_check.ui.repository_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.GithubRepositoryData
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor() : ViewModel() {
    private val _githubRepositoryDetail = MutableLiveData<GithubRepositoryData>()

    val gitHubRepositoryDetails: LiveData<GithubRepositoryData>
        get() = _githubRepositoryDetail

    init {
        logMessage("RepositoryDetail ViewModel initialized")
    }

    fun setRepositoryDetails(githubRepositoryData: GithubRepositoryData) {
        _githubRepositoryDetail.value = githubRepositoryData
        logMessage("Repository details set: $githubRepositoryData")
    }

    override fun onCleared() {
        super.onCleared()
        logMessage("ViewModel cleared")
    }

    private fun logMessage(message: String) {
        Log.d("RepositoryDetailViewModel", message)
    }
}