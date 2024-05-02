package jp.co.yumemi.android.code_check.ui.search_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.ErrorState
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.sources.local.LocalDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing settings and search history.
 */
@HiltViewModel
class SettingViewModel @Inject constructor(private val localDataSource: LocalDataSource) :
    ViewModel() {
    // LiveData to observe all search history entries
    val allSearchHistory: LiveData<List<SearchHistory>> get() = localDataSource.getAllSearchHistory()

    // LiveData to observe error state
    var errorState = MutableLiveData<ErrorState?>()
    val errorLiveData: MutableLiveData<ErrorState?> get() = errorState

    /**
     * Deletes all search history entries.
     */
    fun deleteSearchHistory() {
        viewModelScope.launch {
            try {
                // Delete all search history entries from the data source
                localDataSource.deleteAllSearchHistory()
                Log.d("HomeViewModel", "All search history entries deleted successfully")

            } catch (e: Exception) {
                Log.e(
                    "HomeViewModel",
                    "Failed to delete search history entries, Error: ${e.message}"
                )
            }
        }
    }


}

