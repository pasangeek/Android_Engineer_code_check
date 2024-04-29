package jp.co.yumemi.android.code_check.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepository

@Dao
interface FavoriteRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: FavoriteRepository)

    @Query("SELECT * FROM favorite_repositories")
    suspend fun getAll(): List<FavoriteRepository>

    @Delete
    suspend fun delete(repository: FavoriteRepository)
}