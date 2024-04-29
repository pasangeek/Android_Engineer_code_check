package jp.co.yumemi.android.code_check.ui.favourite_repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.repository.local.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteRepositoryViewModel  @Inject constructor(
    private val repository: RoomRepository,
    private val favoriteRepositoryDao: FavoriteRepositoryDao
): ViewModel() {

    private val _favoriteRepositories = MutableLiveData<List<FavoriteRepositoryEntity>>()
    val favoriteRepositories: LiveData<List<FavoriteRepositoryEntity>> = _favoriteRepositories
    // Maintain a list of favorite repositories
    private val favoriteRepositoryList = mutableListOf<FavoriteRepositoryEntity>()
    init {
        viewModelScope.launch {
            _favoriteRepositories.value = repository.getAllFavoriteRepositories()
            favoriteRepositoryList.addAll(_favoriteRepositories.value ?: emptyList())
        }
    }
    fun isFavorite(repository: FavoriteRepositoryEntity): Boolean {
        return favoriteRepositoryList.any { it.id == repository.id }
    }
    fun getFavoriteRepositories() {
        viewModelScope.launch {
            _favoriteRepositories.value = repository.getAllFavoriteRepositories()
        }
    }

    fun addFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            repository.insert(favouriteRepository)
            getFavoriteRepositories() // Refresh the list after insertion
        }
    }

    fun removeFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteRepository(favouriteRepository)
            getFavoriteRepositories() // Refresh the list after deletion
        }
    }
}