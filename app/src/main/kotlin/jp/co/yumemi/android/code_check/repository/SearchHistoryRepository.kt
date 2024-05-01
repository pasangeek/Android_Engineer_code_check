package jp.co.yumemi.android.code_check.repository

import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.data.database.ApplicationRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import javax.inject.Inject

class SearchHistoryRepository@Inject constructor(private val searchHistoryDao: ApplicationRepositoryDao) {
    val allSearchHistory: LiveData<List<SearchHistory>> = searchHistoryDao.getAllSearchHistory()

    suspend fun insert(searchHistory: SearchHistory) {
        searchHistoryDao.insertSearchHistory(searchHistory)
    }
}