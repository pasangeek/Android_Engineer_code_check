package jp.co.yumemi.android.code_check.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.database.entities.SearchHistory
import jp.co.yumemi.android.code_check.sources.local.LocalDataSource
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val localDataSource: LocalDataSource) : ViewModel() {

    val allSearchHistory: LiveData<List<SearchHistory>> get() = localDataSource.getAllSearchHistory()


    }

