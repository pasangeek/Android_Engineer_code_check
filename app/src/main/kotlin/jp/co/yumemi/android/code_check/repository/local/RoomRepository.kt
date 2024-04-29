package jp.co.yumemi.android.code_check.repository.local

import jp.co.yumemi.android.code_check.data.database.FavoriteRepositoryDao
import jp.co.yumemi.android.code_check.data.database.entities.FavoriteRepositoryEntity
import javax.inject.Inject

class RoomRepository @Inject constructor(
private val dao: FavoriteRepositoryDao){

    suspend fun insert(repository: FavoriteRepositoryEntity) {
        dao.insert(repository)
    }

    suspend fun getAllFavoriteRepositories(): List<FavoriteRepositoryEntity> {
        return dao.getAll()
    }

    suspend fun deleteFavoriteRepository(repository: FavoriteRepositoryEntity) {
        dao.delete(repository)
    }
}