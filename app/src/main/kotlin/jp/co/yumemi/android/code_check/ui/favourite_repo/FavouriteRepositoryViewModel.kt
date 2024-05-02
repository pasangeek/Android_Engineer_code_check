package jp.co.yumemi.android.code_check.ui.favourite_repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.database.ApplicationRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import jp.co.yumemi.android.code_check.sources.local.LocalDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing favorite repositories.
 *
 * @property repository The LocalDataSource instance for database operations.
 * @property applicationRepositoryDao The ApplicationRepositoryDao instance for database operations.
 */
@HiltViewModel
class FavouriteRepositoryViewModel  @Inject constructor(
    private val repository: LocalDataSource,
    private val applicationRepositoryDao: ApplicationRepositoryDao
): ViewModel() {

    // LiveData for holding favorite repositories
    private val _favoriteRepositories = MutableLiveData<List<FavoriteRepositoryEntity>>()
    var readAllData: LiveData<List<FavoriteRepositoryEntity>> = repository.readAllData

    /**
     * Initializes the ViewModel by fetching favorite repositories from the database.
     */
    init {
        viewModelScope.launch {
            try {
                _favoriteRepositories.value = repository.getAllFavoriteRepositories()
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("FavouriteRepoViewModel", "Error fetching favorite repositories", e)
            }
        }
    }
    /**
     * Checks if a given repository is marked as favorite.
     *
     * @param repository The repository to check.
     * @return True if the repository is marked as favorite, false otherwise.
     */
    suspend fun checkIfFavorite(repositoryName: String): Boolean {
        return try {
            repository.isFavorite(repositoryName)
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("FavouriteRepoViewModel", "Error checking if repository is favorite", e)
            false
        }
    }

    /**
     * Fetches all favorite repositories from the database.
     */
    fun getFavoriteRepositories() {
        viewModelScope.launch {
            try {
                _favoriteRepositories.value = repository.getAllFavoriteRepositories()
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("FavouriteRepoViewModel", "Error fetching favorite repositories", e)
            }
        }
    }

    /**
     * Adds a repository to the list of favorite repositories.
     *
     * @param favouriteRepository The repository to add.
     */
    fun addFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            try {
                repository.insert(favouriteRepository)
                getFavoriteRepositories() // Refresh the list after insertion
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("FavouriteRepoViewModel", "Error adding favorite repository", e)
            }
        }
    }
    /**
     * Removes a repository from the list of favorite repositories.
     *
     * @param favouriteRepository The repository to remove.
     */
    fun removeFavoriteRepository(favouriteRepository: FavoriteRepositoryEntity) {
        viewModelScope.launch {
            try {
                // Log before deleting the favorite repository
                Log.d("FavouriteRepoViewModel", "Deleting favorite repository: $favouriteRepository")
                repository.deleteFavoriteRepository(favouriteRepository)
                // Log after deleting the favorite repository
                Log.d("FavouriteRepoViewModel", "Favorite repository deleted: $favouriteRepository")

                // Log before getting favorite repositories
                Log.d("FavouriteRepoViewModel", "Refreshing favorite repositories list...")
                getFavoriteRepositories()
                // Log after getting favorite repositories
                Log.d("FavouriteRepoViewModel", "Favorite repositories list refreshed.")
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("FavouriteRepoViewModel", "Error removing favorite repository", e)
            }
        }
    }
}