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

/**
 * ViewModel responsible for managing favorite repositories.
 * This ViewModel interacts with the database through [RoomRepository] and [FavoriteRepositoryDao]
 * to perform CRUD operations on favorite repositories.
 * It provides LiveData for observing the list of favorite repositories.
 *
 * @property repository The [RoomRepository] instance for interacting with the database.
 * @property favoriteRepositoryDao The [FavoriteRepositoryDao] instance for database operations specific to favorite repositories.
 */
@HiltViewModel
class FavouriteRepositoryViewModel  @Inject constructor(
    private val repository: RoomRepository,
    private val favoriteRepositoryDao: FavoriteRepositoryDao
): ViewModel() {

    private val _favoriteRepositories = MutableLiveData<List<FavoriteRepositoryEntity>>()
    val favoriteRepositories: LiveData<List<FavoriteRepositoryEntity>> = _favoriteRepositories
    // Maintain a list of favorite repositories
    private val favoriteRepositoryList = mutableListOf<FavoriteRepositoryEntity>()

    var readAllData: LiveData<List<FavoriteRepositoryEntity>> = repository.readAllData

    init {
        viewModelScope.launch {

            _favoriteRepositories.value = repository.getAllFavoriteRepositories()
            favoriteRepositoryList.addAll(_favoriteRepositories.value ?: emptyList())

            readAllData = repository.readAllData
        }

    }
    /**
     * Checks if a given repository is marked as favorite.
     *
     * @param repository The repository to check.
     * @return True if the repository is marked as favorite, false otherwise.
     */
    suspend fun checkIfFavorite(repositoryName: String): Boolean {
        return repository.isFavorite(repositoryName)
    }

    /**
     * Fetches all favorite repositories from the database.
     */
    fun getFavoriteRepositories() {
        viewModelScope.launch {
            _favoriteRepositories.value = repository.getAllFavoriteRepositories()
        }
    }

    /**
     * Adds a repository to the list of favorite repositories.
     *
     * @param favouriteRepository The repository to add.
     */
    fun addFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            repository.insert(favouriteRepository)
            getFavoriteRepositories() // Refresh the list after insertion
        }
    }
    /**
     * Removes a repository from the list of favorite repositories.
     *
     * @param favouriteRepository The repository to remove.
     */
    fun removeFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteRepository(favouriteRepository)
            getFavoriteRepositories() // Refresh the list after deletion
        }
    }
}