package jp.co.yumemi.android.code_check.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.repository.SearchHistoryRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val searchHistoryRepository: SearchHistoryRepository) : ViewModel() {
    val allSearchHistory: LiveData<List<SearchHistory>> = searchHistoryRepository.allSearchHistory



}