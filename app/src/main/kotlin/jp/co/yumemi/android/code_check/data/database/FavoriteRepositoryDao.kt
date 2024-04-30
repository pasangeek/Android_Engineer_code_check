package jp.co.yumemi.android.code_check.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
/**
 * Data Access Object (DAO) interface for interacting with the favorite repositories table.
 */
@Dao
interface FavoriteRepositoryDao {
    /**
     * Inserts a favorite repository into the database.
     *
     * @param repository The repository to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: FavoriteRepositoryEntity)
    /**
     * Retrieves all favorite repositories from the database.
     *
     * @return A list of favorite repositories.
     */
    @Query("SELECT * FROM favorite_repositories")
    suspend fun getAll(): List<FavoriteRepositoryEntity>


    @Query("SELECT * FROM favorite_repositories ORDER BY id ASC")
  fun readAllData(): LiveData<List<FavoriteRepositoryEntity>>
    /**
     * Deletes a favorite repository from the database.
     *
     * @param repository The repository to delete.
     */
    @Delete
    suspend fun delete(repository: FavoriteRepositoryEntity)

    @Query("SELECT * FROM favorite_repositories WHERE name = :repositoryName")
    suspend fun isFavorite(repositoryName: String): FavoriteRepositoryEntity?
}