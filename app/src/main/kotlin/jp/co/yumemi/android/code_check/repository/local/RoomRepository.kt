package jp.co.yumemi.android.code_check.repository.local

import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import javax.inject.Inject
/**
 * Repository class responsible for interacting with the Room database.
 * This class provides methods for performing CRUD operations on favorite repositories.
 *
 * @property dao The Data Access Object (DAO) for favorite repositories.
 */
class RoomRepository @Inject constructor(
private val dao: FavoriteRepositoryDao){
    /**
     * Inserts a new favorite repository into the database.
     *
     * @param repository The repository to insert.
     */
    suspend fun insert(repository: FavoriteRepositoryEntity) {
        dao.insert(repository)
    }
    /**
     * Retrieves all favorite repositories from the database.
     *
     * @return A list of favorite repositories.
     */
    suspend fun getAllFavoriteRepositories(): List<FavoriteRepositoryEntity> {
        return dao.getAll()
    }
    /**
     * Deletes a favorite repository from the database.
     *
     * @param repository The repository to delete.
     */
    suspend fun deleteFavoriteRepository(repository: FavoriteRepositoryEntity) {
        dao.delete(repository)
    }
}